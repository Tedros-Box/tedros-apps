/**
 * 
 */
package org.tedros.samples.module.order.model;

import java.text.DateFormat;
import java.util.Date;

import org.tedros.core.TLanguage;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.extension.LocatKey;
import org.tedros.extension.model.Address;
import org.tedros.extension.model.AddressMV;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TButtonField;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TDetailListField;
import org.tedros.fx.annotation.control.TEditEntityModal;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TProcess;
import org.tedros.fx.annotation.control.TShowField;
import org.tedros.fx.annotation.control.TShowField.TField;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTrigger;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.form.TSetting;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.layout.TVBox;
import org.tedros.fx.annotation.layout.TVGrow;
import org.tedros.fx.annotation.page.TPage;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TJoin;
import org.tedros.fx.annotation.query.TOrder;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.query.TTemporal;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TButtonBase;
import org.tedros.fx.annotation.scene.control.TLabeled;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.builder.DateTimeFormatBuilder;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.domain.TLabelPosition;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.model.TFormatter;
import org.tedros.fx.presenter.page.converter.TStringToLong;
import org.tedros.person.ejb.controller.IEmployeeController;
import org.tedros.person.ejb.controller.IPersonController;
import org.tedros.person.model.CostCenter;
import org.tedros.person.model.Employee;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.model.NaturalPerson;
import org.tedros.person.model.Person;
import org.tedros.person.trigger.FilterCostCenterTrigger;
import org.tedros.sample.domain.DomainApp;
import org.tedros.sample.ejb.controller.IGenericDomainController;
import org.tedros.sample.ejb.controller.IOrderController;
import org.tedros.sample.entity.Order;
import org.tedros.sample.entity.OrderItem;
import org.tedros.sample.entity.OrderStatus;
import org.tedros.sample.entity.Sale;
import org.tedros.samples.SmplsKey;
import org.tedros.samples.module.order.action.CreateSaleEvent;
import org.tedros.samples.module.order.builder.CustomerToStringBuilder;
import org.tedros.samples.module.order.builder.DocTypeOtherBuilder;
import org.tedros.samples.module.order.setting.OrderSetting;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TJoinType;
import org.tedros.server.query.TLogicOp;
import org.tedros.stock.module.inventory.builder.CostCenterValBuilder;
import org.tedros.stock.module.inventory.builder.LegalPersonValBuilder;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TSetting(OrderSetting.class)
@TForm(header = "", showBreadcrumBar=false, scroll=false)
@TEjbService(serviceName = IOrderController.JNDI_NAME, model=Order.class)
@TListViewPresenter(listViewMinWidth=450,
	page=@TPage(serviceName = IOrderController.JNDI_NAME,
		query = @TQuery(entity=Order.class, 
			join = { @TJoin(field = "customer", joinAlias = "cs"),
				@TJoin(field = "status",  joinAlias = "st"),
				@TJoin(field = "legalPerson",  joinAlias = "lp"),
				@TJoin(field = "costCenter",  joinAlias = "cc")},
			condition= { 
				@TCondition(field = "name", alias="cs", operator=TCompareOp.LIKE, label=TUsualKey.CUSTOMER),
				@TCondition(field = "id", operator=TCompareOp.EQUAL, label=TUsualKey.CODE, converter=TStringToLong.class),
				@TCondition(field = "date", operator=TCompareOp.GREATER_EQ_THAN, label=TUsualKey.DATE, temporal=TTemporal.DATE),
				@TCondition(field = "name", alias="lp", operator=TCompareOp.LIKE, label=TUsualKey.LEGAL_PERSON)},
			orderBy= { @TOrder(label = TUsualKey.DATE , field = "date"),
				@TOrder(label = TUsualKey.CODE , field = "id"),
				@TOrder(label = TUsualKey.CUSTOMER , field = "name", alias="cs"),
				@TOrder(label = TUsualKey.STATUS , field = "name", alias="st"),
				@TOrder(label = TUsualKey.COST_CENTER , field = "name", alias="cc"),
				@TOrder(label = TUsualKey.LEGAL_PERSON , field = "name", alias="lp")}
	),showSearch=true, showOrderBy=true),
	presenter=@TPresenter(
		decorator = @TDecorator(viewTitle=SmplsKey.VIEW_ORDERS, buildModesRadioButton=false),
		behavior=@TBehavior(runNewActionAfterSave=false, saveOnlyChangedModels=false, saveAllModels=false)))
@TSecurity(id=DomainApp.ORDER_FORM_ID, appName = SmplsKey.APP_SAMPLES,
	moduleName = SmplsKey.MODULE_ORDERS, viewName = SmplsKey.VIEW_ORDERS,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class OrderMV extends TEntityModelView<Order> {
	
	@TLabel(text="Total: ", position=TLabelPosition.LEFT)
	@TText(textStyle = TTextStyle.LARGE)
	@TFieldBox(node=@TNode(id=TFieldBox.INFO, parse = true))
	@THBox(	spacing=10, fillHeight=true,
		pane=@TPane(children={"total", "id", "sale", "createSale"}), 
		hgrow=@THGrow(priority={@TPriority(field="total", priority=Priority.ALWAYS), 
			@TPriority(field="id", priority=Priority.ALWAYS),
			@TPriority(field="sale", priority=Priority.ALWAYS),
			@TPriority(field="createSale", priority=Priority.NEVER)}))
	private SimpleStringProperty total;
	
	@TShowField(fields=@TField(label=TUsualKey.CODE, 
			labelPosition=TLabelPosition.LEFT))
	private SimpleLongProperty id;

	@TShowField(fields=@TField(name="id", label=TUsualKey.SALE_CODE, 
			labelPosition=TLabelPosition.LEFT))
	private SimpleObjectProperty<Sale> sale;
	
	@TButtonField(labeled = @TLabeled(text=SmplsKey.BTN_GENERATE_SALE_RECORD, parse = true),
			buttonBase=@TButtonBase(onAction=CreateSaleEvent.class))
	private SimpleStringProperty createSale;
	
	@TTabPane(tabs = { 
		@TTab( text = TUsualKey.MAIN_DATA, fields={"deliveryAddress"}),
		@TTab(text =  TUsualKey.PRODUCTS, fields={"items"})
	})
	private SimpleLongProperty tabPane;

	@TLabel(text=LocatKey.ADDRESS)
	@TEditEntityModal(height=40,model = Address.class, modelView=AddressMV.class)
	@TGenericType(model = Address.class, modelView=AddressMV.class)
	@THBox(	spacing=10, fillHeight=true,
		pane=@TPane(children={"vbox", "deliveryAddress"}), 
	hgrow=@THGrow(priority={@TPriority(field="vbox", priority=Priority.ALWAYS), 
			@TPriority(field="deliveryAddress", priority=Priority.NEVER)}))
	private SimpleObjectProperty<AddressMV> deliveryAddress;
	
	@TVBox(spacing=10, fillWidth=true,
		pane=@TPane(children={"date", "legalPerson"}), 
		vgrow=@TVGrow(priority={@TPriority(field="date", priority=Priority.ALWAYS), 
				@TPriority(field="legalPerson", priority=Priority.ALWAYS)}))
	private SimpleLongProperty vbox;
	
	@TLabel(text=TUsualKey.DATE_TIME)
	@TDatePickerField(required=true, 
	dateFormat=DateTimeFormatBuilder.class)
	@THBox(	spacing=10, fillHeight=true,
		pane=@TPane(children={"date", "customer", "status"}), 
	hgrow=@THGrow(priority={@TPriority(field="date", priority=Priority.NEVER), 
			@TPriority(field="customer", priority=Priority.ALWAYS),
			@TPriority(field="status", priority=Priority.NEVER)}))
	private SimpleObjectProperty<Date> date;

	@TLabel(text=TUsualKey.CUSTOMER)
	@TAutoCompleteEntity(
			showMaxItems=30,
			converter=CustomerToStringBuilder.class,
			service = IPersonController.JNDI_NAME,
			query = @TQuery(entity = NaturalPerson.class, 
				join = {
						@TJoin(type=TJoinType.LEFT, field = "documents", joinAlias = "doc"),
						@TJoin(type=TJoinType.LEFT, alias="doc", field = "type", joinAlias = "dtp")
				},
				condition = {
					@TCondition(field = "name", operator=TCompareOp.LIKE),
					@TCondition(logicOp=TLogicOp.OR, field = "lastName", 
					operator=TCompareOp.LIKE), 
					@TCondition(logicOp=TLogicOp.OR, alias="doc", field = "value", 
					operator=TCompareOp.LIKE), 
					@TCondition(logicOp=TLogicOp.AND, alias="dtp", field = "docType", 
					operator=TCompareOp.NOT_EQ,
					valueBuilder=DocTypeOtherBuilder.class, prompted=false)}))
	private SimpleObjectProperty<NaturalPerson> customer;
	
	@TLabel(text=TUsualKey.STATUS)
	@TComboBoxField(required=true,
	process=@TProcess(service = IGenericDomainController.JNDI_NAME, 
			 query=@TQuery(entity=OrderStatus.class)))
	private SimpleObjectProperty<OrderStatus> status;

	@TLabel(text=TUsualKey.LEGAL_PERSON)
	@TAutoCompleteEntity(required=true, 
	startSearchAt=3, showMaxItems=30,
	service = IPersonController.JNDI_NAME,
	query = @TQuery(entity = LegalPerson.class, 
		condition = {
			@TCondition(field = "name", operator=TCompareOp.LIKE),
			@TCondition(logicOp=TLogicOp.OR, field = "otherName", 
			operator=TCompareOp.LIKE)}))
	@TTrigger(triggerClass = FilterCostCenterTrigger.class, 
	targetFieldName="costCenter", runAfterFormBuild=true)
	@THBox(	spacing=10, fillHeight=true,
	pane=@TPane(children={"legalPerson", "costCenter", "seller"}), 
	hgrow=@THGrow(priority={
			@TPriority(field="legalPerson", priority=Priority.ALWAYS),
			@TPriority(field="costCenter", priority=Priority.NEVER),
			@TPriority(field="seller", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<LegalPerson> legalPerson;
	
	@TLabel(text=TUsualKey.COST_CENTER)
	@TComboBoxField(required=true)
	private SimpleObjectProperty<CostCenter> costCenter;
	
	@TLabel(text=TUsualKey.EMPLOYEE)
	@TAutoCompleteEntity(
	startSearchAt=3, showMaxItems=30,
	service = IEmployeeController.JNDI_NAME,
	query = @TQuery(entity = Employee.class, 
		condition = {
			@TCondition(field = "name", operator=TCompareOp.LIKE),
			@TCondition(logicOp=TLogicOp.OR, field = "lastName", 
			operator=TCompareOp.LIKE), 
			@TCondition(logicOp=TLogicOp.AND, field = "legalPerson", 
			valueBuilder=LegalPersonValBuilder.class, prompted=false),
			@TCondition(logicOp=TLogicOp.AND, field = "costCenter", 
			valueBuilder=CostCenterValBuilder.class, prompted=false)}))
	private SimpleObjectProperty<Employee> seller;

	@TLabel(text=TUsualKey.PRODUCTS, show=false)
	@TFieldBox(node=@TNode(id="Orderitem", parse = true))
	@TDetailListField(required=true, region=@TRegion(maxHeight=500, parse = false),
	modelView = OrderItemMV.class, entity = OrderItem.class)
	@TGenericType(model=OrderItem.class, modelView=OrderItemMV.class)
	private ITObservableList<OrderItemMV> items;
	
	@SuppressWarnings("rawtypes")
	public OrderMV(Order entity) {
		super(entity);
		super.formatToString(TFormatter.create()
				.add(id, obj ->{
					Long v = (Long) obj;
					return v > 0 ? TUsualKey.CODE+": "+v: "";
				})
				.add(date, obj ->{
					Date dt = (Date) obj;
					return " "+TUsualKey.ON+" "+DateFormat
						.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, TLanguage.getLocale())
						.format(dt);
				})
				.add(customer, obj ->{
					String name = obj instanceof TEntityModelView 
							?  ((StringProperty)((TEntityModelView) obj).getProperty("name")).getValue()
									: ((Person)obj).getName();
					return " "+TUsualKey.TO+" "+name;
				})
				.add(" "+TUsualKey.WITH+" "+ TUsualKey.STATUS+": %s", status));
	}
	
	/**
	 * @return the items
	 */
	public ITObservableList<OrderItemMV> getItems() {
		return items;
	}

	/**
	 * @return the total
	 */
	public SimpleStringProperty getTotal() {
		return total;
	}

	/**
	 * @return the legalPerson
	 */
	public SimpleObjectProperty<LegalPerson> getLegalPerson() {
		return legalPerson;
	}

	/**
	 * @return the costCenter
	 */
	public SimpleObjectProperty<CostCenter> getCostCenter() {
		return costCenter;
	}

	/**
	 * @return the date
	 */
	public SimpleObjectProperty<Date> getDate() {
		return date;
	}

	/**
	 * @return the deliveryAddress
	 */
	public SimpleObjectProperty<AddressMV> getDeliveryAddress() {
		return deliveryAddress;
	}

}

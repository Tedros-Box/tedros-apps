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
import org.tedros.fx.annotation.control.TAutoCompleteEntity.TEntry;
import org.tedros.fx.annotation.control.TButtonField;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TContent;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TDetailListField;
import org.tedros.fx.annotation.control.TEditEntityModal;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.control.TOneSelectionModal;
import org.tedros.fx.annotation.control.TOptionsList;
import org.tedros.fx.annotation.control.TShowField;
import org.tedros.fx.annotation.control.TShowField.TField;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTrigger;
import org.tedros.fx.annotation.form.TDetailForm;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.form.TSetting;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TButtonBase;
import org.tedros.fx.annotation.scene.control.TLabeled;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.annotation.view.TOption;
import org.tedros.fx.annotation.view.TPaginator;
import org.tedros.fx.annotation.view.TPaginator.TJoin;
import org.tedros.fx.builder.DateTimeFormatBuilder;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.domain.TLabelPosition;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.fx.presenter.model.TFormatter;
import org.tedros.person.ejb.controller.IEmployeeController;
import org.tedros.person.ejb.controller.IPersonController;
import org.tedros.person.model.CostCenter;
import org.tedros.person.model.Employee;
import org.tedros.person.model.FindPersonMV;
import org.tedros.person.model.LegalPerson;
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
import org.tedros.samples.module.order.setting.OrderSetting;

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
@TForm(name = "", showBreadcrumBar=false, scroll=false)
@TEjbService(serviceName = IOrderController.JNDI_NAME, model=Order.class)
@TListViewPresenter(listViewMinWidth=450,
	paginator=@TPaginator(entityClass = Order.class, serviceName = IOrderController.JNDI_NAME,
		show=true, showSearch=true,  promptText=TUsualKey.CUSTOMER,
		searchField="name", fieldAlias="cs",
		join = { @TJoin(field = "customer", joinAlias = "cs"),
				@TJoin(field = "status",  joinAlias = "st")},
		orderBy = {	@TOption(text = TUsualKey.DATE , field = "date"),
				@TOption(text = TUsualKey.CODE , field = "id"),
				@TOption(text = TUsualKey.CUSTOMER , field = "name", alias="cs"),
				@TOption(text = TUsualKey.STATUS , field = "name", alias="st")}),
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
		@TTab( text = TUsualKey.MAIN_DATA, 
			content = @TContent(detailForm=@TDetailForm(fields={"date","status", "customer"}))),
		@TTab(text =  TUsualKey.PRODUCTS, 
			content = @TContent(detailForm=@TDetailForm(fields={"items"})))
	})
	private SimpleLongProperty code;
		
	@TLabel(text=TUsualKey.DATE_TIME)
	@TDatePickerField(required=true, 
	dateFormat=DateTimeFormatBuilder.class)
	@THBox(	spacing=10, fillHeight=true,
		pane=@TPane(children={"date", "legalPerson", "costCenter"}), 
	hgrow=@THGrow(priority={@TPriority(field="date", priority=Priority.ALWAYS), 
			@TPriority(field="legalPerson", priority=Priority.ALWAYS),
			@TPriority(field="costCenter", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<Date> date;

	@TLabel(text=TUsualKey.LEGAL_PERSON)
	@TAutoCompleteEntity(required=true,
	startSearchAt=2, showMaxItems=30,
	entries = @TEntry(entityType = LegalPerson.class, 
	fields = {"name","otherName"}, 
	service = IPersonController.JNDI_NAME))
	@TTrigger(triggerClass = FilterCostCenterTrigger.class, 
	targetFieldName="costCenter", runAfterFormBuild=true)
	protected SimpleObjectProperty<LegalPerson> legalPerson;
	
	@TLabel(text=TUsualKey.COST_CENTER)
	@TComboBoxField(required=true)
	private SimpleObjectProperty<CostCenter> costCenter;
	
	@TLabel(text=TUsualKey.STATUS)
	@TComboBoxField(required=true,
	optionsList=@TOptionsList(serviceName = IGenericDomainController.JNDI_NAME, 
	entityClass=OrderStatus.class))
	@THBox(	spacing=10, fillHeight=true,
		pane=@TPane(children={"status", "seller"}), 
		hgrow=@THGrow(priority={@TPriority(field="status", priority=Priority.ALWAYS),
				@TPriority(field="seller", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<OrderStatus> status;

	@TLabel(text=TUsualKey.EMPLOYEE)
	@TAutoCompleteEntity(
			entries = @TEntry(entityType = Employee.class, 
			fields = { "name" }, service = IEmployeeController.JNDI_NAME))
	private SimpleObjectProperty<Employee> seller;
	
	@TLabel(text=TUsualKey.CUSTOMER)
	@TOneSelectionModal(height=40, required=true,
	modelClass = Person.class, modelViewClass = FindPersonMV.class)
	@THBox(	spacing=10, fillHeight=true,
		pane=@TPane(children={"customer", "deliveryAddress"}), 
	hgrow=@THGrow(priority={@TPriority(field="customer", priority=Priority.NEVER), 
		@TPriority(field="deliveryAddress", priority=Priority.NEVER)}))
	@TModelViewType(modelClass = Person.class, modelViewClass=FindPersonMV.class)
	private SimpleObjectProperty<FindPersonMV> customer;
	
	@TLabel(text=LocatKey.ADDRESS)
	@TEditEntityModal(height=40,modelClass = Address.class, modelViewClass=AddressMV.class)
	@TModelViewType(modelClass = Address.class, modelViewClass=AddressMV.class)
	private SimpleObjectProperty<AddressMV> deliveryAddress;

	@TLabel(text=TUsualKey.PRODUCTS, show=false)
	@TFieldBox(node=@TNode(id="Orderitem", parse = true))
	@TDetailListField(required=true, region=@TRegion(maxHeight=500, parse = false),
	entityModelViewClass = OrderItemMV.class, entityClass = OrderItem.class)
	@TModelViewType(modelClass=OrderItem.class, modelViewClass=OrderItemMV.class)
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

}

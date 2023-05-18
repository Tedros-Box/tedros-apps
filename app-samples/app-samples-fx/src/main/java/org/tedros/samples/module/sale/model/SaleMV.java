/**
 * 
 */
package org.tedros.samples.module.sale.model;

import java.util.Date;
import java.util.Locale;

import org.tedros.core.TLanguage;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TAutoCompleteEntity.TEntry;
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
import org.tedros.location.LocatKey;
import org.tedros.location.model.Address;
import org.tedros.location.module.address.model.AddressMV;
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
import org.tedros.sample.ejb.controller.ISaleController;
import org.tedros.sample.entity.Sale;
import org.tedros.sample.entity.SaleItem;
import org.tedros.sample.entity.SaleStatus;
import org.tedros.sample.entity.SaleType;
import org.tedros.samples.SmplsKey;
import org.tedros.samples.module.sale.setting.SaleSetting;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TSetting(SaleSetting.class)
@TForm(name = "", showBreadcrumBar=false, scroll=false)
@TEjbService(serviceName = ISaleController.JNDI_NAME, model=Sale.class)
@TListViewPresenter(listViewMinWidth=400,
	paginator=@TPaginator(entityClass = Sale.class, serviceName = ISaleController.JNDI_NAME,
		show=true, showSearch=true, searchField="name", fieldAlias="cs",
		join = { @TJoin(field = "customer", joinAlias = "cs"),
				@TJoin(field = "legalPerson",  joinAlias = "lp"),
				@TJoin(field = "costCenter",  joinAlias = "cc")},
		orderBy = {	@TOption(text = TUsualKey.DATE , field = "date"),
				@TOption(text = TUsualKey.CUSTOMER , field = "name", alias="cs"),
				@TOption(text = TUsualKey.COST_CENTER , field = "name", alias="cc"),
				@TOption(text = TUsualKey.LEGAL_PERSON , field = "name", alias="lp")}),
	presenter=@TPresenter(
		decorator = @TDecorator(viewTitle=SmplsKey.VIEW_SALES, buildModesRadioButton=false),
		behavior=@TBehavior(runNewActionAfterSave=false, saveOnlyChangedModels=false, saveAllModels=true)))
@TSecurity(id=DomainApp.SALE_FORM_ID, appName = SmplsKey.APP_SAMPLES,
	moduleName = SmplsKey.MODULE_SALES, viewName = SmplsKey.VIEW_SALES,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class SaleMV extends TEntityModelView<Sale> {
	
	@TLabel(text="Total: ", position=TLabelPosition.LEFT)
	@TText(textStyle = TTextStyle.LARGE)
	@TFieldBox(node=@TNode(id=TFieldBox.INFO, parse = true))
	private SimpleStringProperty total;
	
	@TTabPane(tabs = { 
		@TTab( text = TUsualKey.MAIN_DATA, 
			content = @TContent(detailForm=@TDetailForm(fields={"date","type", "customer"}))),
		@TTab(text =  TUsualKey.PRODUCTS, 
			content = @TContent(detailForm=@TDetailForm(fields={"items"})))
	})
	private SimpleLongProperty id;
		
	@TLabel(text=TUsualKey.DATE_TIME)
	@TDatePickerField(required=true, 
	dateFormat=DateTimeFormatBuilder.class)
	@THBox(	spacing=10, fillHeight=true,
		pane=@TPane(children={"date", "legalPerson", "costCenter"}), 
	hgrow=@THGrow(priority={@TPriority(field="date", priority=Priority.NEVER), 
			@TPriority(field="legalPerson", priority=Priority.NEVER),
			@TPriority(field="costCenter", priority=Priority.NEVER)}))
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
	
	@TLabel(text=TUsualKey.TYPE)
	@TComboBoxField(required=true,
	optionsList=@TOptionsList(serviceName = IGenericDomainController.JNDI_NAME, 
	entityClass=SaleType.class))
	@THBox(	spacing=10, fillHeight=true,
		pane=@TPane(children={"type", "status", "seller"}), 
	hgrow=@THGrow(priority={@TPriority(field="type", priority=Priority.NEVER), 
			@TPriority(field="status", priority=Priority.NEVER),
		@TPriority(field="seller", priority=Priority.NEVER)}))
	private SimpleObjectProperty<SaleType> type;
	
	@TLabel(text=TUsualKey.STATUS)
	@TComboBoxField(required=true,
	optionsList=@TOptionsList(serviceName = IGenericDomainController.JNDI_NAME, 
	entityClass=SaleStatus.class))
	private SimpleObjectProperty<SaleStatus> status;

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
	@TFieldBox(node=@TNode(id="saleitem", parse = true))
	@TDetailListField(required=true, region=@TRegion(maxHeight=500, parse = false),
	entityModelViewClass = SaleItemMV.class, entityClass = SaleItem.class)
	@TModelViewType(modelClass=SaleItem.class, modelViewClass=SaleItemMV.class)
	private ITObservableList<SaleItemMV> items;
	
	public SaleMV(Sale entity) {
		super(entity);
		if(entity.isNew())
			date.setValue(new Date());
		String dtf = TLanguage.getLocale().equals(new Locale("pt"))
				? "em %5$td/%5$tm/%5$tY às %5$tT"
						: "on %5$tm-%5$td-%5$tY at %5$tT";
		super.formatToString("%s [%s], %s, %s "+dtf, legalPerson, costCenter, customer, type, date);
	}

	/**
	 * @return the items
	 */
	public ITObservableList<SaleItemMV> getItems() {
		return items;
	}

	/**
	 * @return the total
	 */
	public SimpleStringProperty getTotal() {
		return total;
	}

}

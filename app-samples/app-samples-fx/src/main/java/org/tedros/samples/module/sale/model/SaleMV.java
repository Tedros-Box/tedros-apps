/**
 * 
 */
package org.tedros.samples.module.sale.model;

import java.util.Date;

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
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.domain.TLabelPosition;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.location.LocatKey;
import org.tedros.location.model.Address;
import org.tedros.location.module.address.model.AddressMV;
import org.tedros.person.PersonKeys;
import org.tedros.person.ejb.controller.IEmployeeController;
import org.tedros.person.model.Employee;
import org.tedros.person.model.FindPersonMV;
import org.tedros.person.model.Person;
import org.tedros.sample.domain.DomainApp;
import org.tedros.sample.ejb.controller.IGenericDomainController;
import org.tedros.sample.ejb.controller.ISaleController;
import org.tedros.sample.entity.Sale;
import org.tedros.sample.entity.SaleItem;
import org.tedros.sample.entity.SaleStatus;
import org.tedros.sample.entity.SaleType;
import org.tedros.samples.SmplsKey;
import org.tedros.samples.module.sale.setting.SaleSetting;
import org.tedros.stock.STCKKey;

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
@TListViewPresenter(
	paginator=@TPaginator(entityClass = Sale.class, serviceName = ISaleController.JNDI_NAME,
		show=true, showSearchField=false,  
		orderBy = {	@TOption(text = TUsualKey.DATE , value = "date")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=SmplsKey.VIEW_SALES,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false, saveOnlyChangedModels=false, saveAllModels=true)))
@TSecurity(id=DomainApp.SALE_FORM_ID, appName = SmplsKey.APP_SAMPLES,
	moduleName = SmplsKey.MODULE_SALES, viewName = SmplsKey.VIEW_SALES,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class SaleMV extends TEntityModelView<Sale> {
	
	@TLabel(text="Total: ", position=TLabelPosition.LEFT)
	@TText(textStyle = TTextStyle.LARGE)
	private SimpleStringProperty total;
	
	@TTabPane(tabs = { 
		@TTab( text = TUsualKey.MAIN_DATA, 
			content = @TContent(detailForm=@TDetailForm(fields={"date","customer"}))),
		@TTab(text =  STCKKey.PRODUCTS, 
			content = @TContent(detailForm=@TDetailForm(fields={"items"})))
	})
	private SimpleLongProperty id;
		
	@TLabel(text=TUsualKey.DATE)
	@TDatePickerField
	@THBox(	spacing=10, fillHeight=true,
		pane=@TPane(children={"date", "type", "status", "seller"}), 
	hgrow=@THGrow(priority={@TPriority(field="type", priority=Priority.NEVER), 
			@TPriority(field="status", priority=Priority.NEVER),
		@TPriority(field="seller", priority=Priority.NEVER),
		@TPriority(field="date", priority=Priority.NEVER)}))
	private SimpleObjectProperty<Date> date;

	@TLabel(text=TUsualKey.TYPE)
	@TComboBoxField(required=true,
	optionsList=@TOptionsList(serviceName = IGenericDomainController.JNDI_NAME, 
	entityClass=SaleType.class))
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
	
	@TLabel(text=PersonKeys.CUSTOMER)
	@TOneSelectionModal(height=80,
	modelClass = Person.class, modelViewClass = FindPersonMV.class)
	@THBox(	spacing=10, fillHeight=true,
		pane=@TPane(children={"customer", "deliveryAddress"}), 
	hgrow=@THGrow(priority={@TPriority(field="customer", priority=Priority.NEVER), 
		@TPriority(field="deliveryAddress", priority=Priority.NEVER)}))
	private SimpleObjectProperty<Person> customer;
	
	@TLabel(text=LocatKey.ADDRESS)
	@TEditEntityModal(modelClass = Address.class, modelViewClass=AddressMV.class)
	@TModelViewType(modelClass = Address.class, modelViewClass=AddressMV.class)
	private SimpleObjectProperty<AddressMV> deliveryAddress;

	@TFieldBox(node=@TNode(id="saleitem", parse = true))
	@TDetailListField(required=true, region=@TRegion(maxHeight=500, parse = false),
	entityModelViewClass = SaleItemMV.class, entityClass = SaleItem.class)
	@TModelViewType(modelClass=SaleItem.class, modelViewClass=SaleItemMV.class)
	private ITObservableList<SaleItemMV> items;
	
	public SaleMV(Sale entity) {
		super(entity);
	}

	/**
	 * @return the date
	 */
	public SimpleObjectProperty<Date> getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(SimpleObjectProperty<Date> date) {
		this.date = date;
	}

	/**
	 * @return the customer
	 */
	public SimpleObjectProperty<Person> getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(SimpleObjectProperty<Person> customer) {
		this.customer = customer;
	}

	/**
	 * @return the seller
	 */
	public SimpleObjectProperty<Employee> getSeller() {
		return seller;
	}

	/**
	 * @param seller the seller to set
	 */
	public void setSeller(SimpleObjectProperty<Employee> seller) {
		this.seller = seller;
	}

	/**
	 * @return the type
	 */
	public SimpleObjectProperty<SaleType> getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(SimpleObjectProperty<SaleType> type) {
		this.type = type;
	}

	/**
	 * @return the status
	 */
	public SimpleObjectProperty<SaleStatus> getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(SimpleObjectProperty<SaleStatus> status) {
		this.status = status;
	}

	/**
	 * @return the id
	 */
	public SimpleLongProperty getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	/**
	 * @return the deliveryAddress
	 */
	public SimpleObjectProperty<AddressMV> getDeliveryAddress() {
		return deliveryAddress;
	}

	/**
	 * @param deliveryAddress the deliveryAddress to set
	 */
	public void setDeliveryAddress(SimpleObjectProperty<AddressMV> deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	/**
	 * @return the items
	 */
	public ITObservableList<SaleItemMV> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(ITObservableList<SaleItemMV> items) {
		this.items = items;
	}

	/**
	 * @return the total
	 */
	public SimpleStringProperty getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(SimpleStringProperty total) {
		this.total = total;
	}

}

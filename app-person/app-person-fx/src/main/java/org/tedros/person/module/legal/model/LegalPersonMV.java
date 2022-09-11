/**
 * 
 */
package org.tedros.person.module.legal.model;

import java.util.Date;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.docs.export.ModalDocumentMV;
import org.tedros.docs.model.Document;
import org.tedros.extension.contact.model.ContactMV;
import org.tedros.extension.model.Contact;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TCallbackFactory;
import org.tedros.fx.annotation.control.TCellFactory;
import org.tedros.fx.annotation.control.TCellValueFactory;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TContent;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TDetailListField;
import org.tedros.fx.annotation.control.TEditEntityModal;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.control.TOptionsList;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TDetailForm;
import org.tedros.fx.annotation.form.TForm;
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
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.view.TOption;
import org.tedros.fx.annotation.view.TPaginator;
import org.tedros.fx.builder.TEditModelRowFactoryCallBackBuilder;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.tablecell.TShortDateCallback;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.location.LocatKey;
import org.tedros.location.model.Address;
import org.tedros.location.module.address.model.AddressMV;
import org.tedros.person.PersonKeys;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.ejb.controller.ILegalPersonController;
import org.tedros.person.ejb.controller.ILegalTypeController;
import org.tedros.person.model.Employee;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.model.LegalType;
import org.tedros.person.model.PersonAttributes;
import org.tedros.person.model.PersonAttributesMV;
import org.tedros.person.model.PersonEvent;
import org.tedros.person.model.PersonEventMV;
import org.tedros.person.module.legal.table.EmployeeITemMV;
import org.tedros.person.module.legal.table.StaffTypeCellCallBack;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */

@TForm(name = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = ILegalPersonController.JNDI_NAME, model=LegalPerson.class)
@TListViewPresenter(
	paginator=@TPaginator(entityClass = LegalPerson.class, serviceName = ILegalPersonController.JNDI_NAME,
		show=true, showSearchField=true, searchFieldName="name", 
		orderBy = {	@TOption(text = TUsualKey.CORPORATE_NAME , value = "name"),
				@TOption(text = TUsualKey.TRADE_NAME , value = "otherName")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=PersonKeys.VIEW_LEGAL_PERSON,
		buildModesRadioButton=false),
		behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.LEGAL_PERSON_FORM_ID, appName = PersonKeys.APP_PERSON,
	moduleName = PersonKeys.MODULE_LEGAL_PERSON, viewName = PersonKeys.VIEW_LEGAL_PERSON,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class LegalPersonMV extends TEntityModelView<LegalPerson> {

	
	@TTabPane(tabs = { 
		@TTab(text = TUsualKey.MAIN_DATA,
			content = @TContent(detailForm=@TDetailForm(fields={"name","type", "address"}))),  
		@TTab(text = TUsualKey.STAFF,
			content = @TContent(detailForm=@TDetailForm(fields={"staff"}))),
		@TTab(text = TUsualKey.DESCRIPTION, 
			content = @TContent(detailForm=@TDetailForm(fields={"description"}))),
		@TTab(text = TUsualKey.OBSERVATION, 
			content = @TContent(detailForm=@TDetailForm(fields={"observation"}))), 
		@TTab(text = TUsualKey.EVENTS,
		content = @TContent(detailForm=@TDetailForm(fields={"events"})))
	})
	private SimpleLongProperty id;
	
	@TLabel(text=TUsualKey.CORPORATE_NAME)
	@TTextField(maxLength=120, required = true,
		node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"name", "otherName"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS), 
			@TPriority(field="otherName", priority=Priority.ALWAYS)}))
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.TRADE_NAME)
	@TTextField(maxLength=60)
	private SimpleStringProperty otherName;
	

	@TLabel(text=TUsualKey.TYPE)
	@TComboBoxField(firstItemText=TUsualKey.SELECT,
	optionsList=@TOptionsList(serviceName = ILegalTypeController.JNDI_NAME, 
	optionModelViewClass=LegalTypeMV.class,
	entityClass=LegalType.class))
	@THBox(	pane=@TPane(children={"type", "startActivities", "endActivities"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="type", priority=Priority.ALWAYS), 
			@TPriority(field="startActivities", priority=Priority.ALWAYS), 
			@TPriority(field="endActivities", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<LegalType> type;
	
	@TLabel(text=TUsualKey.START_ACTIVITIES)
	@TDatePickerField
	private SimpleObjectProperty<Date> startActivities;
	
	@TLabel(text=TUsualKey.END_ACTIVITIES)
	@TDatePickerField
	private SimpleObjectProperty<Date> endActivities;
	
	@TLabel(text=LocatKey.ADDRESS)
	@TEditEntityModal(modelClass = Address.class, modelViewClass=AddressMV.class)
	@TModelViewType(modelClass = Address.class, modelViewClass=AddressMV.class)
	@THBox(	pane=@TPane(children={"address", "contacts", "documents", "attributes"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="address", priority=Priority.ALWAYS), 
			@TPriority(field="contacts", priority=Priority.ALWAYS), 
			@TPriority(field="attributes", priority=Priority.ALWAYS), 
			@TPriority(field="documents", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<AddressMV> address;
	
	@TLabel(text=TUsualKey.ATTRIBUTES)
	@TEditEntityModal(modelClass = PersonAttributes.class, modelViewClass=PersonAttributesMV.class)
	@TModelViewType(modelClass = PersonAttributes.class, modelViewClass=PersonAttributesMV.class)
	private ITObservableList<PersonAttributesMV> attributes;
	
	@TLabel(text=TUsualKey.CONTACTS)
	@TEditEntityModal(modelClass = Contact.class, modelViewClass=ContactMV.class)
	@TModelViewType(modelClass = Contact.class, modelViewClass=ContactMV.class)
	private ITObservableList<ContactMV> contacts;
	
	@TLabel(text=TUsualKey.DOCUMENTS)
	@TEditEntityModal(modelClass = Document.class, modelViewClass=ModalDocumentMV.class)
	@TModelViewType(modelClass=Document.class, modelViewClass=ModalDocumentMV.class)
	public ITObservableList<ModalDocumentMV> documents;

	@TTableView(editable=true, rowFactory=TEditModelRowFactoryCallBackBuilder.class,
		control=@TControl(tooltip=TFxKey.TABLE_MENU_TOOLTIP, parse = true),
		columns = { 
			@TTableColumn(cellValue="toStringProperty", text = TUsualKey.NAME, prefWidth=20, resizable=true), 
			@TTableColumn(cellValue="type", text = TUsualKey.OCCUPATION, resizable=true,
				cellValueFactory=@TCellValueFactory(parse=true, 
				value=@TCallbackFactory(parse=true, value=StaffTypeCellCallBack.class))), 
			@TTableColumn(cellValue="hiringDate", text = TUsualKey.HIRING_DATE, resizable=true,
				cellFactory=@TCellFactory(parse = true, 
					callBack=@TCallbackFactory(parse=true, value=TShortDateCallback.class))), 
			@TTableColumn(cellValue="resignationDate", text = TUsualKey.RESIGNATION_DATE, resizable=true,
				cellFactory=@TCellFactory(parse = true, 
					callBack=@TCallbackFactory(parse=true, value=TShortDateCallback.class))),
	})
	@TModelViewType(modelClass = Employee.class, modelViewClass=EmployeeITemMV.class)
	private ITObservableList<EmployeeITemMV> staff;

	@TFieldBox(node=@TNode(id="evdtl", parse = true))
	@TDetailListField(entityModelViewClass = PersonEventMV.class, entityClass = PersonEvent.class)
	@TModelViewType(modelClass=PersonEvent.class, modelViewClass=PersonEventMV.class)
	private ITObservableList<PersonEventMV> events;

	@TTextAreaField(maxLength=2000, wrapText=true)
	private SimpleStringProperty description;
	
	@TTextAreaField(maxLength=2000, wrapText=true)
	private SimpleStringProperty observation;
	
	
	public LegalPersonMV(LegalPerson entity) {
		super(entity);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	@Override
	public SimpleStringProperty toStringProperty() {
		return name;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getOtherName() {
		return otherName;
	}

	public void setOtherName(SimpleStringProperty otherName) {
		this.otherName = otherName;
	}

	public SimpleObjectProperty<LegalType> getType() {
		return type;
	}

	public void setType(SimpleObjectProperty<LegalType> type) {
		this.type = type;
	}

	public SimpleObjectProperty<Date> getStartActivities() {
		return startActivities;
	}

	public void setStartActivities(SimpleObjectProperty<Date> startActivities) {
		this.startActivities = startActivities;
	}

	public SimpleObjectProperty<Date> getEndActivities() {
		return endActivities;
	}

	public void setEndActivities(SimpleObjectProperty<Date> endActivities) {
		this.endActivities = endActivities;
	}

	public SimpleStringProperty getDescription() {
		return description;
	}

	public void setDescription(SimpleStringProperty description) {
		this.description = description;
	}

	public SimpleStringProperty getObservation() {
		return observation;
	}

	public void setObservation(SimpleStringProperty observation) {
		this.observation = observation;
	}

	public SimpleObjectProperty<AddressMV> getAddress() {
		return address;
	}

	public void setAddress(SimpleObjectProperty<AddressMV> address) {
		this.address = address;
	}

	public ITObservableList<PersonAttributesMV> getAttributes() {
		return attributes;
	}

	public void setAttributes(ITObservableList<PersonAttributesMV> attributes) {
		this.attributes = attributes;
	}

	public ITObservableList<ContactMV> getContacts() {
		return contacts;
	}

	public void setContacts(ITObservableList<ContactMV> contacts) {
		this.contacts = contacts;
	}

	public ITObservableList<ModalDocumentMV> getDocuments() {
		return documents;
	}

	public void setDocuments(ITObservableList<ModalDocumentMV> documents) {
		this.documents = documents;
	}

	public ITObservableList<EmployeeITemMV> getStaff() {
		return staff;
	}

	public void setStaff(ITObservableList<EmployeeITemMV> staff) {
		this.staff = staff;
	}

	public ITObservableList<PersonEventMV> getEvents() {
		return events;
	}

	public void setEvents(ITObservableList<PersonEventMV> events) {
		this.events = events;
	}

}

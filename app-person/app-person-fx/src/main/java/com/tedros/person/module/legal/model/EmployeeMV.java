/**
 * 
 */
package com.tedros.person.module.legal.model;

import java.util.Date;
import java.util.Locale;

import com.tedros.core.TLanguage;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.docs.export.ModalDocumentMV;
import com.tedros.docs.model.Document;
import com.tedros.extension.contact.model.ContactMV;
import com.tedros.extension.model.Contact;
import com.tedros.fxapi.TUsualKey;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TConverter;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TEditEntityModal;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.annotation.control.TOneSelectionModal;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TRadioButton;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TDetailForm;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.view.TOption;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.location.LocatKey;
import com.tedros.location.model.Address;
import com.tedros.location.module.address.model.AddressMV;
import com.tedros.person.PersonKeys;
import com.tedros.person.converter.GenderConverter;
import com.tedros.person.converter.SexConverter;
import com.tedros.person.domain.DomainApp;
import com.tedros.person.domain.Gender;
import com.tedros.person.domain.Sex;
import com.tedros.person.ejb.controller.IEmployeeController;
import com.tedros.person.ejb.controller.IStaffTypeController;
import com.tedros.person.model.Employee;
import com.tedros.person.model.LegalPerson;
import com.tedros.person.model.PersonAttributes;
import com.tedros.person.model.StaffType;
import com.tedros.person.module.natural.model.PersonAttributesMV;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */

@TForm(name = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = IEmployeeController.JNDI_NAME, model=Employee.class)
@TListViewPresenter(
		paginator=@TPaginator(entityClass = Employee.class, serviceName = IEmployeeController.JNDI_NAME,
		show=true, showSearchField=true, searchFieldName="name", 
		orderBy = {	@TOption(text = TUsualKey.NAME , value = "name"),
				@TOption(text = TUsualKey.LAST_NAME , value = "lastName")}),
		presenter=@TPresenter(decorator = @TDecorator(viewTitle=PersonKeys.VIEW_EMPLOYEES,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.EMPLOYEE_FORM_ID, appName = PersonKeys.APP_PERSON,
	moduleName = PersonKeys.MODULE_LEGAL_PERSON, viewName = PersonKeys.VIEW_EMPLOYEES,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class EmployeeMV extends TEntityModelView<Employee> {

	private SimpleLongProperty id;
	
	@TTabPane(tabs = { 
		@TTab(closable=false, 
			content = @TContent(detailForm=@TDetailForm(fields={"name","type", "sex", "address"})), text = TUsualKey.MAIN_DATA), 
		@TTab(closable=false, 
			content = @TContent(detailForm=@TDetailForm(fields={"description"})), text = TUsualKey.DESCRIPTION),
		@TTab(closable=false, 
			content = @TContent(detailForm=@TDetailForm(fields={"observation"})), text = TUsualKey.OBSERVATION)
	})
	private SimpleStringProperty displayProperty;
	
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=120, required = true,
		node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"name", "lastName", "birthDate"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS), 
			@TPriority(field="lastName", priority=Priority.ALWAYS), 
			@TPriority(field="birthDate", priority=Priority.SOMETIMES)}))
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.LAST_NAME)
	@TTextField(maxLength=60)
	private SimpleStringProperty lastName;
	
	@TLabel(text=TUsualKey.BIRTHDATE)
	@TDatePickerField
	private SimpleObjectProperty<Date> birthDate;

	@TLabel(text=TUsualKey.OCCUPATION)
	@TComboBoxField(firstItemTex=TUsualKey.SELECT,
	optionsList=@TOptionsList(serviceName = IStaffTypeController.JNDI_NAME, 
	optionModelViewClass=StaffTypeMV.class,
	entityClass=StaffType.class))
	@THBox(	pane=@TPane(children={"type", "hiringDate", "resignationDate", "employer"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="type", priority=Priority.ALWAYS), 
			@TPriority(field="hiringDate", priority=Priority.ALWAYS), 
			@TPriority(field="resignationDate", priority=Priority.ALWAYS), 
			@TPriority(field="employer", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<StaffType> type;
	
	@TLabel(text=TUsualKey.HIRING_DATE)
	@TDatePickerField
	private SimpleObjectProperty<Date> hiringDate;
	
	@TLabel(text=TUsualKey.RESIGNATION_DATE)
	@TDatePickerField
	private SimpleObjectProperty<Date> resignationDate;
	
	@TLabel(text=TUsualKey.EMPLOYER)
	@TOneSelectionModal(modelClass = LegalPerson.class, modelViewClass = FindLegalPersonMV.class, 
	width=300, height=50, required=true)
	private SimpleObjectProperty<LegalPerson> employer;
	
	@TLabel(text=TUsualKey.SEX)
	@THorizontalRadioGroup(spacing= 10,
		converter=@TConverter(parse = true, type = SexConverter.class),
		radioButtons = { @TRadioButton(text = TUsualKey.FEMININE, userData = TUsualKey.FEMININE ),
				@TRadioButton(text = TUsualKey.MASCULINE, userData = TUsualKey.MASCULINE )
		})
	@THBox(	pane=@TPane(children={"sex", "gender"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="sex", priority=Priority.ALWAYS), 
			@TPriority(field="gender", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<Sex> sex;
	
	@TLabel(text=TUsualKey.GENDER)
	@THorizontalRadioGroup(spacing= 10,
		converter=@TConverter(parse = true, type = GenderConverter.class),
		radioButtons = { @TRadioButton(text = TUsualKey.FEMININE, userData = TUsualKey.FEMININE),
				@TRadioButton(text = TUsualKey.MASCULINE, userData = TUsualKey.MASCULINE),
				@TRadioButton(text = TUsualKey.NEUTER, userData = TUsualKey.NEUTER),
				@TRadioButton(text = TUsualKey.COMMON, userData = TUsualKey.COMMON)
		})
	private SimpleObjectProperty<Gender> gender;
	
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
	

	@TTextAreaField(maxLength=2000, wrapText=true)
	private SimpleStringProperty description;
	
	@TTextAreaField(maxLength=2000, wrapText=true)
	private SimpleStringProperty observation;
	
	
	
	public EmployeeMV(Employee entity) {
		super(entity);
		if(TLanguage.getLocale().equals(Locale.ENGLISH))
			super.formatFieldsToDisplay("%s, %s", lastName, name);
		else
			super.formatFieldsToDisplay("%s %s", name, lastName);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getDisplayProperty() {
		return displayProperty;
	}

	public void setDisplayProperty(SimpleStringProperty displayProperty) {
		this.displayProperty = displayProperty;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getLastName() {
		return lastName;
	}

	public void setLastName(SimpleStringProperty lastName) {
		this.lastName = lastName;
	}

	public SimpleObjectProperty<Date> getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(SimpleObjectProperty<Date> birthDate) {
		this.birthDate = birthDate;
	}

	public SimpleObjectProperty<StaffType> getType() {
		return type;
	}

	public void setType(SimpleObjectProperty<StaffType> type) {
		this.type = type;
	}

	public SimpleObjectProperty<Date> getHiringDate() {
		return hiringDate;
	}

	public void setHiringDate(SimpleObjectProperty<Date> hiringDate) {
		this.hiringDate = hiringDate;
	}

	public SimpleObjectProperty<Date> getResignationDate() {
		return resignationDate;
	}

	public void setResignationDate(SimpleObjectProperty<Date> resignationDate) {
		this.resignationDate = resignationDate;
	}

	public SimpleObjectProperty<LegalPerson> getEmployer() {
		return employer;
	}

	public void setEmployer(SimpleObjectProperty<LegalPerson> employer) {
		this.employer = employer;
	}

	public SimpleObjectProperty<Sex> getSex() {
		return sex;
	}

	public void setSex(SimpleObjectProperty<Sex> sex) {
		this.sex = sex;
	}

	public SimpleObjectProperty<Gender> getGender() {
		return gender;
	}

	public void setGender(SimpleObjectProperty<Gender> gender) {
		this.gender = gender;
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

}

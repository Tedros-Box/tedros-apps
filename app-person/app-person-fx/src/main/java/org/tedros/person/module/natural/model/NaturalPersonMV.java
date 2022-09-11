/**
 * 
 */
package org.tedros.person.module.natural.model;

import java.util.Date;
import java.util.Locale;

import org.tedros.core.TLanguage;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.docs.export.ModalDocumentMV;
import org.tedros.docs.model.Document;
import org.tedros.extension.contact.model.ContactMV;
import org.tedros.extension.model.Contact;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TContent;
import org.tedros.fx.annotation.control.TConverter;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TDetailListField;
import org.tedros.fx.annotation.control.TEditEntityModal;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.THorizontalRadioGroup;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.control.TRadioButton;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
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
import org.tedros.fx.annotation.view.TOption;
import org.tedros.fx.annotation.view.TPaginator;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.location.LocatKey;
import org.tedros.location.model.Address;
import org.tedros.location.module.address.model.AddressMV;
import org.tedros.person.PersonKeys;
import org.tedros.person.converter.CivilStatusConverter;
import org.tedros.person.converter.GenderConverter;
import org.tedros.person.converter.SexConverter;
import org.tedros.person.domain.CivilStatus;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.domain.Gender;
import org.tedros.person.domain.Sex;
import org.tedros.person.ejb.controller.INaturalPersonController;
import org.tedros.person.model.NaturalPerson;
import org.tedros.person.model.PersonAttributes;
import org.tedros.person.model.PersonAttributesMV;
import org.tedros.person.model.PersonEvent;
import org.tedros.person.model.PersonEventMV;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = INaturalPersonController.JNDI_NAME, model=NaturalPerson.class)
@TListViewPresenter(
		paginator=@TPaginator(entityClass = NaturalPerson.class, serviceName = INaturalPersonController.JNDI_NAME,
		show=true, showSearchField=true, searchFieldName="name", 
		orderBy = {	@TOption(text = TUsualKey.NAME , value = "name"),
					@TOption(text = TUsualKey.LAST_NAME, value = "lastName")}),
		presenter=@TPresenter(decorator = @TDecorator(viewTitle=PersonKeys.VIEW_NATURAL_PERSON,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.NATURAL_PERSON_FORM_ID, appName = PersonKeys.APP_PERSON,
	moduleName = PersonKeys.MODULE_NATURAL_PERSON, viewName = PersonKeys.VIEW_NATURAL_PERSON,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class NaturalPersonMV extends TEntityModelView<NaturalPerson> {

	@TTabPane(tabs = { 
		@TTab( text = TUsualKey.MAIN_DATA,
			content = @TContent(detailForm=@TDetailForm(fields={"name","sex", "address"}))),
		@TTab(text = TUsualKey.DESCRIPTION, 
			content = @TContent(detailForm=@TDetailForm(fields={"description"}))),
		@TTab(text = TUsualKey.OBSERVATION, 
			content = @TContent(detailForm=@TDetailForm(fields={"observation"}))), 
		@TTab(text = TUsualKey.EVENTS,
			content = @TContent(detailForm=@TDetailForm(fields={"events"})))
	})
	private SimpleLongProperty id;
		
	
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
	
	@TLabel(text=TUsualKey.SEX)
	@THorizontalRadioGroup(spacing= 10,
		converter=@TConverter(parse = true, type = SexConverter.class),
		radioButtons = { @TRadioButton(text = TUsualKey.FEMININE, userData = TUsualKey.FEMININE ),
				@TRadioButton(text = TUsualKey.MASCULINE, userData = TUsualKey.MASCULINE )
		})
	@THBox(	pane=@TPane(children={"sex", "gender", "civilStatus"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="sex", priority=Priority.ALWAYS), 
			@TPriority(field="gender", priority=Priority.ALWAYS), 
			@TPriority(field="civilStatus", priority=Priority.ALWAYS)}))
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

	@TLabel(text=TUsualKey.CIVIL_STATUS)
	@THorizontalRadioGroup(spacing= 10,
		converter=@TConverter(parse = true, type = CivilStatusConverter.class),
		radioButtons = { @TRadioButton(text = TUsualKey.SINGLE, userData = TUsualKey.SINGLE),
				@TRadioButton(text = TUsualKey.MARRIED, userData = TUsualKey.MARRIED),
				@TRadioButton(text = TUsualKey.SEPARATED, userData = TUsualKey.SEPARATED),
				@TRadioButton(text = TUsualKey.WIDOWED, userData = TUsualKey.WIDOWED)
		})
	private SimpleObjectProperty<CivilStatus> civilStatus;
	
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
	@TEditEntityModal(modalHeight=400, modalWidth=600,
	modelClass = PersonAttributes.class, modelViewClass=PersonAttributesMV.class)
	@TModelViewType(modelClass = PersonAttributes.class, modelViewClass=PersonAttributesMV.class)
	private ITObservableList<PersonAttributesMV> attributes;
	
	@TLabel(text=TUsualKey.CONTACTS)
	@TEditEntityModal(modalHeight=400, modalWidth=600,
	modelClass = Contact.class, modelViewClass=ContactMV.class)
	@TModelViewType(modelClass = Contact.class, modelViewClass=ContactMV.class)
	private ITObservableList<ContactMV> contacts;
	
	@TLabel(text=TUsualKey.DOCUMENTS)
	@TEditEntityModal(modalHeight=490, modalWidth=700,
	modelClass = Document.class, modelViewClass=ModalDocumentMV.class)
	@TModelViewType(modelClass=Document.class, modelViewClass=ModalDocumentMV.class)
	public ITObservableList<ModalDocumentMV> documents;

	@TFieldBox(node=@TNode(id="evdtl", parse = true))
	@TDetailListField(entityModelViewClass = PersonEventMV.class, entityClass = PersonEvent.class)
	@TModelViewType(modelClass=PersonEvent.class, modelViewClass=PersonEventMV.class)
	private ITObservableList<PersonEventMV> events;

	@TTextAreaField(maxLength=2000, wrapText=true)
	private SimpleStringProperty description;
	
	@TTextAreaField(maxLength=2000, wrapText=true)
	private SimpleStringProperty observation;

	public NaturalPersonMV(NaturalPerson entity) {
		super(entity);
		if(TLanguage.getLocale().equals(Locale.ENGLISH))
			super.formatToString("%s, %s", lastName, name);
		else
			super.formatToString("%s %s", name, lastName);
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

	public SimpleObjectProperty<CivilStatus> getCivilStatus() {
		return civilStatus;
	}

	public void setCivilStatus(SimpleObjectProperty<CivilStatus> civilStatus) {
		this.civilStatus = civilStatus;
	}

	public ITObservableList<PersonEventMV> getEvents() {
		return events;
	}

	public void setEvents(ITObservableList<PersonEventMV> events) {
		this.events = events;
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

}

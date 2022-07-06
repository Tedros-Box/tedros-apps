/**
 * 
 */
package com.tedros.person.module.legal.model;

import java.util.Date;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.docs.export.ModalDocumentMV;
import com.tedros.docs.model.Document;
import com.tedros.extension.contact.model.ContactMV;
import com.tedros.extension.model.Contact;
import com.tedros.fxapi.TUsualKey;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TEditEntityModal;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
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
import com.tedros.person.domain.DomainApp;
import com.tedros.person.ejb.controller.ILegalPersonController;
import com.tedros.person.ejb.controller.ILegalTypeController;
import com.tedros.person.model.LegalPerson;
import com.tedros.person.model.LegalType;
import com.tedros.person.model.PersonAttributes;
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

	private SimpleLongProperty id;
	
	private SimpleStringProperty displayProperty;
	

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
	@TComboBoxField(firstItemTex=TUsualKey.SELECT,
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
	
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(maxLength=2000, wrapText=true, prefRowCount=5)
	@THBox(	pane=@TPane(children={"description", "observation"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="description", priority=Priority.ALWAYS), 
			@TPriority(field="observation", priority=Priority.ALWAYS)}))
	private SimpleStringProperty description;
	
	@TLabel(text=TUsualKey.OBSERVATION)
	@TTextAreaField(maxLength=2000, wrapText=true, prefRowCount=5)
	private SimpleStringProperty observation;
	
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
	
	public LegalPersonMV(LegalPerson entity) {
		super(entity);
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

}

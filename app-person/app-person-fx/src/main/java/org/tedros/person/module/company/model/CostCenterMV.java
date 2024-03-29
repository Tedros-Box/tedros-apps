/**
 * 
 */
package org.tedros.person.module.company.model;

import java.util.Date;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.model.TFormatter;
import org.tedros.extension.model.Address;
import org.tedros.extension.model.AddressMV;
import org.tedros.extension.model.Contact;
import org.tedros.extension.model.ContactMV;
import org.tedros.extension.model.Document;
import org.tedros.extension.model.ModalDocumentMV;
import org.tedros.extension.module.doc.trigger.DocumentTrigger;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TEditEntityModal;
import org.tedros.fx.annotation.control.TFileField;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.control.TTrigger;
import org.tedros.fx.annotation.form.TForm;
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
import org.tedros.fx.annotation.query.TOrder;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.builder.DateTimeFormatBuilder;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.domain.TFileExtension;
import org.tedros.fx.domain.TFileModelType;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.property.TSimpleFileProperty;
import org.tedros.person.PersonKeys;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.ejb.controller.ICostCenterController;
import org.tedros.person.ejb.controller.IPersonController;
import org.tedros.person.model.CostCenter;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.model.Person;
import org.tedros.person.table.PersonTV;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TLogicOp;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Orientation;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(header = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = ICostCenterController.JNDI_NAME, model=CostCenter.class)
@TListViewPresenter(
	page=@TPage(serviceName = ICostCenterController.JNDI_NAME,
	query = @TQuery(entity=CostCenter.class, condition= {
			@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME)},
		orderBy= {@TOrder(label = TUsualKey.NAME, field = "name")}
			),showSearch=true, showOrderBy=true),
	presenter=@TPresenter(
		decorator = @TDecorator(viewTitle=PersonKeys.VIEW_COST_CENTER, buildModesRadioButton=false),
		behavior=@TBehavior(runNewActionAfterSave=false, saveAllModels=false, saveOnlyChangedModels=false)))
@TSecurity(id=DomainApp.COST_CENTER_FORM_ID, appName = PersonKeys.APP_PERSON,
	moduleName = PersonKeys.MODULE_LEGAL_PERSON, viewName = PersonKeys.VIEW_COST_CENTER,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class CostCenterMV extends TEntityModelView<CostCenter> {

	@TTabPane(
		tabs = { 
			@TTab(text = TUsualKey.MAIN_DATA, orientation=Orientation.HORIZONTAL, 
					fields = {"description", "address"}),
			@TTab(text = TUsualKey.OBSERVATION, fields = {"observation"}),
			@TTab(text = TUsualKey.PICTURES, fields = {"image"})})
	private SimpleLongProperty id;
	
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(wrapText=true)
	@TVBox(	spacing=10, fillWidth=true,
	pane=@TPane(children={"code", "responsable", "description"}), 
	vgrow=@TVGrow(priority={@TPriority(field="code", priority=Priority.NEVER), 
			@TPriority(field="responsable", priority=Priority.NEVER), 
			@TPriority(field="description", priority=Priority.ALWAYS)}))
	private SimpleStringProperty description;
	
	@TLabel(text=TUsualKey.CODE)
	@TTextField(maxLength=60,
	node=@TNode(requestFocus=true, parse = true) )
	@THBox(	pane=@TPane(children={"code", "name"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="code", priority=Priority.NEVER), 
			@TPriority(field="name", priority=Priority.ALWAYS)}))
	private SimpleStringProperty code;
	
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=120, required = true)
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.RESPONSABLE)
	@TAutoCompleteEntity(modelViewType=PersonTV.class, 
	startSearchAt=2, showMaxItems=30,
	service = IPersonController.JNDI_NAME,
	query = @TQuery(entity = Person.class, 
	condition = {
		@TCondition(field = "name", operator=TCompareOp.LIKE),
		@TCondition(logicOp=TLogicOp.OR, field = "lastName", operator=TCompareOp.LIKE)}))
	@THBox(spacing=10, fillHeight=true,	
	pane=@TPane(children={"responsable", "legalPerson", "openingDate", "closingDate"}),
	hgrow=@THGrow(priority={@TPriority(field="responsable", priority=Priority.ALWAYS),
			@TPriority(field="legalPerson", priority=Priority.ALWAYS), 
			@TPriority(field="openingDate", priority=Priority.NEVER), 
			@TPriority(field="closingDate", priority=Priority.NEVER)}))
	private SimpleObjectProperty<PersonTV> responsable;

	@TLabel(text=TUsualKey.LEGAL_PERSON)
	@TAutoCompleteEntity(required=true, 
	startSearchAt=3, showMaxItems=30,
	service = IPersonController.JNDI_NAME,
	query = @TQuery(entity = LegalPerson.class, 
		condition = {
			@TCondition(field = "name", operator=TCompareOp.LIKE),
			@TCondition(logicOp=TLogicOp.OR, field = "otherName", operator=TCompareOp.LIKE)}))
	private SimpleObjectProperty<LegalPerson> legalPerson;
	
	@TLabel(text=TUsualKey.OPENING_DATE)
	@TDatePickerField(dateFormat=DateTimeFormatBuilder.class)
	private SimpleObjectProperty<Date> openingDate;

	@TLabel(text=TUsualKey.CLOSING_DATE)
	@TDatePickerField(dateFormat=DateTimeFormatBuilder.class)
	private SimpleObjectProperty<Date> closingDate;
	
	@TLabel(text=TUsualKey.ADDRESS)
	@TEditEntityModal(height=80, 
	model = Address.class, modelView=AddressMV.class)
	@TVBox(	spacing=10, fillWidth=true,
	pane=@TPane(children={"address", "contacts", "documents"}), 
	vgrow=@TVGrow(priority={@TPriority(field="address", priority=Priority.ALWAYS), 
			@TPriority(field="contacts", priority=Priority.ALWAYS), 
			@TPriority(field="documents", priority=Priority.ALWAYS)}))
	@TGenericType(model = Address.class, modelView=AddressMV.class)
	protected SimpleObjectProperty<AddressMV> address;
	
	@TLabel(text=TUsualKey.CONTACTS)
	@TEditEntityModal(height=80, modalHeight=400, modalWidth=600,
	model = Contact.class, modelView=ContactMV.class)
	@TGenericType(model = Contact.class, modelView=ContactMV.class)
	protected ITObservableList<ContactMV> contacts;
	
	@TLabel(text=TUsualKey.DOCUMENTS)
	@TEditEntityModal(height=80, modalHeight=490, modalWidth=700,
	model = Document.class, modelView=ModalDocumentMV.class)
	@TTrigger(type = DocumentTrigger.class)
	@TGenericType(model=Document.class, modelView=ModalDocumentMV.class)
	protected ITObservableList<ModalDocumentMV> documents;
	
	@TLabel(text=TUsualKey.IMAGE)
	@TFileField(propertyValueType=TFileModelType.ENTITY, preLoadFileBytes=true,
	extensions= {TFileExtension.ALL_IMAGES}, showFilePath=true, showImage=true)
	@TGenericType(model=TFileEntity.class)
	private TSimpleFileProperty<TFileEntity> image;

	@TTextAreaField(wrapText=true)
	private SimpleStringProperty observation;

	public CostCenterMV(CostCenter entity) {
		super(entity);
		super.formatToString(TFormatter.create()
				.add("%s", legalPerson)
				.add(code, obj->" "+obj.toString())
				.add(" %s", name)
			);
	}


	public SimpleLongProperty getId() {
		return id;
	}


	public void setId(SimpleLongProperty id) {
		this.id = id;
	}


	public SimpleStringProperty getCode() {
		return code;
	}


	public void setCode(SimpleStringProperty code) {
		this.code = code;
	}


	public SimpleStringProperty getName() {
		return name;
	}


	public void setName(SimpleStringProperty name) {
		this.name = name;
	}


	public SimpleObjectProperty<Date> getOpeningDate() {
		return openingDate;
	}


	public void setOpeningDate(SimpleObjectProperty<Date> openingDate) {
		this.openingDate = openingDate;
	}


	public SimpleObjectProperty<Date> getClosingDate() {
		return closingDate;
	}


	public void setClosingDate(SimpleObjectProperty<Date> closingDate) {
		this.closingDate = closingDate;
	}


	public SimpleObjectProperty<PersonTV> getResponsable() {
		return responsable;
	}


	public void setResponsable(SimpleObjectProperty<PersonTV> responsable) {
		this.responsable = responsable;
	}


	public SimpleObjectProperty<AddressMV> getAddress() {
		return address;
	}


	public void setAddress(SimpleObjectProperty<AddressMV> address) {
		this.address = address;
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


	public TSimpleFileProperty<TFileEntity> getImage() {
		return image;
	}


	public void setImage(TSimpleFileProperty<TFileEntity> image) {
		this.image = image;
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


	/**
	 * @return the legalPerson
	 */
	public SimpleObjectProperty<LegalPerson> getLegalPerson() {
		return legalPerson;
	}


	/**
	 * @param legalPerson the legalPerson to set
	 */
	public void setLegalPerson(SimpleObjectProperty<LegalPerson> legalPerson) {
		this.legalPerson = legalPerson;
	}

}

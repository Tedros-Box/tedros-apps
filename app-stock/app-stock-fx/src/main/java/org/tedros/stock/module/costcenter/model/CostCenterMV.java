/**
 * 
 */
package org.tedros.stock.module.costcenter.model;

import java.util.Date;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.docs.export.ModalDocumentMV;
import org.tedros.docs.model.Document;
import org.tedros.extension.contact.model.ContactMV;
import org.tedros.extension.model.Contact;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TAutoCompleteEntity.TEntry;
import org.tedros.fx.annotation.control.TContent;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TEditEntityModal;
import org.tedros.fx.annotation.control.TFileField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TModelViewType;
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
import org.tedros.fx.annotation.layout.TVBox;
import org.tedros.fx.annotation.layout.TVGrow;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.view.TOption;
import org.tedros.fx.annotation.view.TPaginator;
import org.tedros.fx.builder.DateTimeFormatBuilder;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.domain.TFileExtension;
import org.tedros.fx.domain.TFileModelType;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.fx.property.TSimpleFileProperty;
import org.tedros.location.LocatKey;
import org.tedros.location.model.Address;
import org.tedros.location.module.address.model.AddressMV;
import org.tedros.person.ejb.controller.IPersonController;
import org.tedros.person.model.NaturalPerson;
import org.tedros.person.table.PersonTV;
import org.tedros.stock.STCKKey;
import org.tedros.stock.domain.DomainApp;
import org.tedros.stock.ejb.controller.ICostCenterController;
import org.tedros.stock.entity.CostCenter;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Orientation;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = ICostCenterController.JNDI_NAME, model=CostCenter.class)
@TListViewPresenter(
	paginator=@TPaginator(entityClass = CostCenter.class, serviceName = ICostCenterController.JNDI_NAME,
		show=true, showSearchField=true, searchFieldName="name", 
		orderBy = {	@TOption(text = TUsualKey.NAME , value = "name")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=STCKKey.VIEW_COST_CENTER,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.COST_CENTER_FORM_ID, appName = STCKKey.APP_STOCK,
	moduleName = STCKKey.MODULE_COST_CENTER, viewName = STCKKey.VIEW_COST_CENTER,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class CostCenterMV extends TEntityModelView<CostCenter> {

	@TTabPane(
		tabs = { 
			@TTab(text = TUsualKey.MAIN_DATA, 
				content = @TContent(detailForm=@TDetailForm(
						orientation=Orientation.HORIZONTAL,
						fields = {"description", "address"}))),
			@TTab(text = TUsualKey.OBSERVATION, 
				content = @TContent(detailForm=@TDetailForm(
					fields = {"observation"}))),
			@TTab(text = TUsualKey.PICTURES, 
				content = @TContent(detailForm=@TDetailForm(
						fields = {"image"}))) })
	private SimpleLongProperty id;
	
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(wrapText=true)
	@TVBox(	spacing=10, fillWidth=true,
	pane=@TPane(children={"code", "responsable", "description"}), 
	vgrow=@TVGrow(priority={@TPriority(field="code", priority=Priority.ALWAYS), 
			@TPriority(field="responsable", priority=Priority.ALWAYS), 
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
	entries = @TEntry(entityType = NaturalPerson.class, fields = {"name","lastName"}, 
	service = IPersonController.JNDI_NAME))
	@THBox(spacing=10, fillHeight=true,	
	pane=@TPane(children={"responsable", "openingDate", "closingDate"}),
	hgrow=@THGrow(priority={@TPriority(field="responsable", priority=Priority.ALWAYS), 
			@TPriority(field="openingDate", priority=Priority.NEVER), 
			@TPriority(field="closingDate", priority=Priority.NEVER)}))
	private SimpleObjectProperty<PersonTV> responsable;
	
	@TLabel(text=TUsualKey.OPENING_DATE)
	@TDatePickerField(dateFormat=DateTimeFormatBuilder.class)
	private SimpleObjectProperty<Date> openingDate;

	@TLabel(text=TUsualKey.CLOSING_DATE)
	@TDatePickerField(dateFormat=DateTimeFormatBuilder.class)
	private SimpleObjectProperty<Date> closingDate;
	
	@TLabel(text=LocatKey.ADDRESS)
	@TEditEntityModal(height=80, 
	modelClass = Address.class, modelViewClass=AddressMV.class)
	@TVBox(	spacing=10, fillWidth=true,
	pane=@TPane(children={"address", "contacts", "documents"}), 
	vgrow=@TVGrow(priority={@TPriority(field="address", priority=Priority.ALWAYS), 
			@TPriority(field="contacts", priority=Priority.ALWAYS), 
			@TPriority(field="documents", priority=Priority.ALWAYS)}))
	@TModelViewType(modelClass = Address.class, modelViewClass=AddressMV.class)
	protected SimpleObjectProperty<AddressMV> address;
	
	@TLabel(text=TUsualKey.CONTACTS)
	@TEditEntityModal(height=80, modalHeight=400, modalWidth=600,
	modelClass = Contact.class, modelViewClass=ContactMV.class)
	@TModelViewType(modelClass = Contact.class, modelViewClass=ContactMV.class)
	protected ITObservableList<ContactMV> contacts;
	
	@TLabel(text=TUsualKey.DOCUMENTS)
	@TEditEntityModal(height=80, modalHeight=490, modalWidth=700,
	modelClass = Document.class, modelViewClass=ModalDocumentMV.class)
	@TModelViewType(modelClass=Document.class, modelViewClass=ModalDocumentMV.class)
	protected ITObservableList<ModalDocumentMV> documents;
	
	@TLabel(text=TUsualKey.IMAGE)
	@TFileField(propertyValueType=TFileModelType.ENTITY, preLoadFileBytes=true,
	extensions= {TFileExtension.ALL_IMAGES}, showFilePath=true, showImage=true)
	@TModelViewType(modelClass=TFileEntity.class)
	private TSimpleFileProperty<TFileEntity> image;

	@TTextAreaField(wrapText=true)
	private SimpleStringProperty observation;

	
	public CostCenterMV(CostCenter entity) {
		super(entity);
		super.formatToString("%s %s", code, name);
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

}

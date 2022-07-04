/**
 * 
 */
package com.tedros.docs.module.model;

import java.util.Date;

import com.tedros.common.model.TFileEntity;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.docs.TDocsKey;
import com.tedros.docs.domain.DomainApp;
import com.tedros.docs.ejb.controller.IDocumentController;
import com.tedros.docs.ejb.controller.IDocumentStateController;
import com.tedros.docs.ejb.controller.IDocumentTypeController;
import com.tedros.docs.model.Document;
import com.tedros.docs.model.DocumentEvent;
import com.tedros.docs.model.DocumentState;
import com.tedros.docs.model.DocumentType;
import com.tedros.docs.module.builder.TNotifyBuilder;
import com.tedros.extension.contact.model.ContactMV;
import com.tedros.extension.model.Contact;
import com.tedros.fxapi.TUsualKey;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TDetailListField;
import com.tedros.fxapi.annotation.control.TEditEntityModal;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TFileField;
import com.tedros.fxapi.annotation.control.THTMLEditor;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TShowField;
import com.tedros.fxapi.annotation.control.TShowField.TField;
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
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.view.TOption;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.domain.TFileExtension;
import com.tedros.fxapi.domain.TFileModelType;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.property.TSimpleFileProperty;
import com.tedros.tools.annotation.TNotifyLink;
import com.tedros.util.TDateUtil;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "#{form.doc}", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = IDocumentController.JNDI_NAME, model=Document.class)
@TListViewPresenter(
		paginator=@TPaginator(entityClass = Document.class, serviceName = IDocumentController.JNDI_NAME,
		show=true, showSearchField=true, searchFieldName="name", 
		orderBy = {	@TOption(text = TUsualKey.REF_CODE, value = "code"),
					@TOption(text = TUsualKey.NAME, value = "name")}),
		presenter=@TPresenter(decorator = @TDecorator(viewTitle=TDocsKey.VIEW_DOCS,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.DOCUMENT_FORM_ID, 
	appName = TDocsKey.APP_DOCS, moduleName = TDocsKey.MODULE_DOCS, viewName = TDocsKey.VIEW_DOCS,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class DocumentMV extends TEntityModelView<Document> {
	
	private SimpleStringProperty displayProperty;
	
	@TTabPane(tabs = { 
		@TTab(closable=false, 
			content = @TContent(detailForm=@TDetailForm(fields={"code","value", "type", "contacts", "insertDate"})), text = TUsualKey.MAIN_DATA), 
		@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"content"})), text = TUsualKey.CONTENT), 
		@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"observation"})), text = TUsualKey.OBSERVATION), 
		@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"events"})), text = TUsualKey.EVENTS)
	})
	private SimpleLongProperty id;
	
	@TLabel(text=TUsualKey.REF_CODE)
	@TTextField(maxLength=10,  node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"code","name"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS), 
			@TPriority(field="code", priority=Priority.NEVER)}))
	private SimpleStringProperty code;
	
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=120, required = true, 
	node=@TNode(requestFocus=true, parse = true) )
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.ADDITIONAL_DATA)
	@TTextAreaField(wrapText=true, prefRowCount=4)
	private SimpleStringProperty value;

	@TLabel(text=TUsualKey.TYPE)
	@TComboBoxField(firstItemTex=TUsualKey.SELECT,
	optionsList=@TOptionsList(serviceName = IDocumentTypeController.JNDI_NAME, 
	optionModelViewClass=DocumentTypeMV.class,
	entityClass=DocumentType.class))
	@THBox(	pane=@TPane(children={"type","state"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={
			@TPriority(field="type", priority=Priority.NEVER),
			@TPriority(field="state", priority=Priority.NEVER)}))
	private SimpleObjectProperty<DocumentType> type;
	
	@TLabel(text=TUsualKey.STATE)
	@TComboBoxField(firstItemTex=TUsualKey.SELECT,
	optionsList=@TOptionsList(serviceName = IDocumentStateController.JNDI_NAME, 
	optionModelViewClass=DocumentStateMV.class,
	entityClass=DocumentState.class))
	private SimpleObjectProperty<DocumentState> state;
	
	@TLabel(text=TUsualKey.CONTACTS)
	@TEditEntityModal(height=100, modelClass = Contact.class, modelViewClass=ContactMV.class)
	@TModelViewType(modelClass = Contact.class, modelViewClass=ContactMV.class)
	@THBox(	pane=@TPane(children={"contacts","file"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="contacts", priority=Priority.NEVER), 
			@TPriority(field="file", priority=Priority.ALWAYS)}))
	private ITObservableList<ContactMV> contacts;

	@TLabel(text=TUsualKey.FILE)
	@TFileField(propertyValueType=TFileModelType.ENTITY, preLoadFileBytes=true,
	extensions= {TFileExtension.ALL_FILES}, showFilePath=true)
	@TModelViewType(modelClass=TFileEntity.class)
	private TSimpleFileProperty<TFileEntity> file;
	
	@TLabel(text="#{label.date.insert}")
	@TShowField(fields= {@TField(pattern=TDateUtil.DDMMYYYY_HHMM)})
	@THBox(	pane=@TPane(children={"insertDate","lastUpdate", "notification"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="insertDate", priority=Priority.NEVER), 
						@TPriority(field="lastUpdate", priority=Priority.NEVER), 
						@TPriority(field="notification", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<Date> insertDate;
	
	@TLabel(text="#{label.date.update}")
	@TShowField(fields= {@TField(pattern=TDateUtil.DDMMYYYY_HHMM)})
	private SimpleObjectProperty<Date> lastUpdate;
	
	@TNotifyLink(entityBuilder = TNotifyBuilder.class)
	private SimpleStringProperty notification;
	
	@TTextAreaField(wrapText=true)
	private SimpleStringProperty observation;
	
	@TFieldBox(node=@TNode(id="evdtl", parse = true))
	@TDetailListField(entityModelViewClass = DocumentEventMV.class, entityClass = DocumentEvent.class)
	@TModelViewType(modelClass=DocumentEvent.class, modelViewClass=DocumentEventMV.class)
	private ITObservableList<DocumentEventMV> events;
	
	@THTMLEditor(control=@TControl( maxHeight=500, parse = true))
	private SimpleStringProperty content;
	
	public DocumentMV(Document entity) {
		super(entity);
		super.formatFieldsToDisplay("%s %s", code, name);
	}
	
	@Override
	public void reload(Document model) {
		super.reload(model);
		super.formatFieldsToDisplay("%s %s", code, name);
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

	public SimpleObjectProperty<DocumentType> getType() {
		return type;
	}

	public void setType(SimpleObjectProperty<DocumentType> type) {
		this.type = type;
	}

	public SimpleObjectProperty<DocumentState> getState() {
		return state;
	}

	public void setState(SimpleObjectProperty<DocumentState> state) {
		this.state = state;
	}

	public ITObservableList<ContactMV> getContacts() {
		return contacts;
	}

	public void setContacts(ITObservableList<ContactMV> contacts) {
		this.contacts = contacts;
	}

	public SimpleStringProperty getObservation() {
		return observation;
	}

	public void setObservation(SimpleStringProperty observation) {
		this.observation = observation;
	}

	public TSimpleFileProperty<TFileEntity> getFile() {
		return file;
	}

	public void setFile(TSimpleFileProperty<TFileEntity> file) {
		this.file = file;
	}

	public ITObservableList<DocumentEventMV> getEvents() {
		return events;
	}

	public void setEvents(ITObservableList<DocumentEventMV> events) {
		this.events = events;
	}

	public SimpleStringProperty getContent() {
		return content;
	}

	public void setContent(SimpleStringProperty content) {
		this.content = content;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return displayProperty;
	}

	public SimpleObjectProperty<Date> getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(SimpleObjectProperty<Date> insertDate) {
		this.insertDate = insertDate;
	}

	public SimpleObjectProperty<Date> getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(SimpleObjectProperty<Date> lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public SimpleStringProperty getNotification() {
		return notification;
	}

	public void setNotification(SimpleStringProperty notification) {
		this.notification = notification;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getValue() {
		return value;
	}

	public void setValue(SimpleStringProperty value) {
		this.value = value;
	}

	public void setDisplayProperty(SimpleStringProperty displayProperty) {
		this.displayProperty = displayProperty;
	}


}

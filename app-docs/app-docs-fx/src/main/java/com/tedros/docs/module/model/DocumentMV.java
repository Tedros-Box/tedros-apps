/**
 * 
 */
package com.tedros.docs.module.model;

import java.util.Date;

import com.tedros.common.model.TFileEntity;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
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
import com.tedros.fxapi.annotation.layout.TVBox;
import com.tedros.fxapi.annotation.layout.TVGrow;
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
		show=true, showSearchField=true, searchFieldName="title", 
		orderBy = {	@TOption(text = "#{label.ref.code}", value = "code"),
					@TOption(text = "#{label.title}", value = "title")}),
		presenter=@TPresenter(decorator = @TDecorator(viewTitle="#{view.docs}",
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.DOCUMENT_FORM_ID, 
	appName = "#{app.docs}", moduleName = "#{module.docs}", viewName = "#{view.docs}",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class DocumentMV extends TEntityModelView<Document> {
	
	private SimpleStringProperty displayProperty = new SimpleStringProperty();
	
	@TTabPane(tabs = { 
		@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"text", "insertDate"})), text = "#{label.main.data}"), 
		@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"content"})), text = "#{label.content}"), 
		@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"events"})), text = "#{label.events}")
	})
	private SimpleLongProperty id;
	
	
	@THBox(	pane=@TPane(children={"code", "file"}), spacing=10, fillHeight=true,
			hgrow=@THGrow(priority={@TPriority(field="code", priority=Priority.SOMETIMES), 
					@TPriority(field="file", priority=Priority.ALWAYS)}))
	private SimpleStringProperty text;
	
	@TLabel(text="#{label.ref.code}")
	@TTextField(maxLength=10,  node=@TNode(requestFocus=true, parse = true))
	@TVBox(	pane=@TPane(children={"code","title","type","state", "contacts"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="title", priority=Priority.ALWAYS), 
			@TPriority(field="code", priority=Priority.ALWAYS),
			@TPriority(field="type", priority=Priority.ALWAYS),
			@TPriority(field="state", priority=Priority.ALWAYS), 
			@TPriority(field="contacts", priority=Priority.ALWAYS)}))
	private SimpleStringProperty code;
	
	@TLabel(text="#{label.title}")
	@TTextField(maxLength=60, required = true, 
	node=@TNode(requestFocus=true, parse = true) )
	private SimpleStringProperty title;

	@TLabel(text="#{label.type}")
	@TComboBoxField(firstItemTex="#{label.select}",
	optionsList=@TOptionsList(serviceName = IDocumentTypeController.JNDI_NAME, 
	optionModelViewClass=DocumentTypeMV.class,
	entityClass=DocumentType.class))
	private SimpleObjectProperty<DocumentType> type;
	
	@TLabel(text="#{label.state}")
	@TComboBoxField(firstItemTex="#{label.select}",
	optionsList=@TOptionsList(serviceName = IDocumentStateController.JNDI_NAME, 
	optionModelViewClass=DocumentStateMV.class,
	entityClass=DocumentState.class))
	private SimpleObjectProperty<DocumentState> state;
	
	@TLabel(text="#{label.contacts}")
	@TEditEntityModal(height=100, modelClass = Contact.class, modelViewClass=ContactMV.class)
	@TModelViewType(modelClass = Contact.class, modelViewClass=ContactMV.class)
	private ITObservableList<ContactMV> contacts;

	@TLabel(text="#{label.file}")
	@TFileField(propertyValueType=TFileModelType.ENTITY, preLoadFileBytes=true,
	extensions= {TFileExtension.ALL_FILES}, showFilePath=true)
	@TVBox(	pane=@TPane(children={"summary","file", "notification"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="summary", priority=Priority.ALWAYS), 
						@TPriority(field="file", priority=Priority.ALWAYS), 
						@TPriority(field="notification", priority=Priority.ALWAYS)}))
	@TModelViewType(modelClass=TFileEntity.class)
	private TSimpleFileProperty<TFileEntity> file;
	
	@TLabel(text="#{label.summary}")
	@TTextAreaField(maxLength=600, wrapText=true, prefRowCount=8)
	@THBox(	pane=@TPane(children={"summary","observation"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="summary", priority=Priority.ALWAYS), 
						@TPriority(field="observation", priority=Priority.ALWAYS)}))
	private SimpleStringProperty summary;

	@TLabel(text="#{label.observation}")
	@TTextAreaField(maxLength=400, wrapText=true, prefRowCount=4)
	private SimpleStringProperty observation;
	
	@TLabel(text="#{label.date.insert}")
	@TShowField(fields= {@TField(pattern=TDateUtil.DDMMYYYY_HHMM)})
	@THBox(	pane=@TPane(children={"insertDate","lastUpdate"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="insertDate", priority=Priority.ALWAYS), 
						@TPriority(field="lastUpdate", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<Date> insertDate;
	
	@TLabel(text="#{label.date.update}")
	@TShowField(fields= {@TField(pattern=TDateUtil.DDMMYYYY_HHMM)})
	private SimpleObjectProperty<Date> lastUpdate;
	
	@TNotifyLink(entityBuilder = TNotifyBuilder.class)
	private SimpleStringProperty notification;
	
	@TFieldBox(node=@TNode(id="evdtl", parse = true))
	@TDetailListField(entityModelViewClass = DocumentEventMV.class, entityClass = DocumentEvent.class)
	@TModelViewType(modelClass=DocumentEvent.class, modelViewClass=DocumentEventMV.class)
	private ITObservableList<DocumentEventMV> events;
	
	@THTMLEditor(control=@TControl( maxHeight=500, parse = true))
	private SimpleStringProperty content;
	
	public DocumentMV(Document entity) {
		super(entity);
		super.formatFieldsToDisplay("%s %s", code, title);
	}
	
	@Override
	public void reload(Document model) {
		super.reload(model);
		super.formatFieldsToDisplay("%s %s", code, title);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}


	public SimpleStringProperty getText() {
		return text;
	}

	public void setText(SimpleStringProperty text) {
		this.text = text;
	}

	public SimpleStringProperty getCode() {
		return code;
	}

	public void setCode(SimpleStringProperty code) {
		this.code = code;
	}

	public SimpleStringProperty getTitle() {
		return title;
	}

	public void setTitle(SimpleStringProperty title) {
		this.title = title;
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

	public SimpleStringProperty getSummary() {
		return summary;
	}

	public void setSummary(SimpleStringProperty summary) {
		this.summary = summary;
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


}

/**
 * 
 */
package com.tedros.docs.module.model;

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
import com.tedros.fxapi.annotation.reader.TDetailReaderHtml;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTextReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.domain.TFileExtension;
import com.tedros.fxapi.domain.TFileModelType;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.property.TSimpleFileProperty;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = "#{form.doc}", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = IDocumentController.JNDI_NAME, model=Document.class)
@TListViewPresenter(presenter=@TPresenter(decorator = @TDecorator(viewTitle="#{view.docs}"),
	behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.DOCUMENT_FORM_ID, 
	appName = "#{app.docs}", moduleName = "#{module.docs}", viewName = "#{view.docs}",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class DocumentMV extends TEntityModelView<Document> {

	
	@TTextReaderHtml(text="#{label.docs}", 
			htmlTemplateForControlValue="<h2 id='"+THtmlConstant.ID+"' name='"+THtmlConstant.NAME+"' style='"+THtmlConstant.STYLE+"'>"+THtmlConstant.CONTENT+"</h2>",
			cssForControlValue="width:100%; padding:8px; background-color: "+TStyleParameter.PANEL_BACKGROUND_COLOR+";",
			cssForHtmlBox="", cssForContentValue="color:"+TStyleParameter.PANEL_TEXT_COLOR+";")
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-fieldbox-title", parse = true))
	@TText(text="#{header.docs}", textAlignment=TextAlignment.LEFT, 
			textStyle = TTextStyle.LARGE)
	private SimpleStringProperty header;
	
	@TTabPane(tabs = { 
		@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"text", "file"})), text = "#{label.main.data}"), 
		@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"content"})), text = "#{label.content}"), 
		@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"events"})), text = "#{label.events}")
	})
	private SimpleLongProperty id;
	
	
	@THBox(	pane=@TPane(children={"code", "summary"}), spacing=10, fillHeight=true,
			hgrow=@THGrow(priority={@TPriority(field="code", priority=Priority.SOMETIMES), 
					@TPriority(field="summary", priority=Priority.ALWAYS)}))
	private SimpleStringProperty text;
	
	@TReaderHtml
	@TLabel(text="#{label.ref.code}")
	@TTextField(maxLength=10, required = true, node=@TNode(requestFocus=true, parse = true))
	@TVBox(	pane=@TPane(children={"code","title","type","state", "contacts"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="title", priority=Priority.ALWAYS), 
			@TPriority(field="code", priority=Priority.ALWAYS),
			@TPriority(field="type", priority=Priority.ALWAYS),
			@TPriority(field="state", priority=Priority.ALWAYS), 
			@TPriority(field="contacts", priority=Priority.ALWAYS)}))
	private SimpleStringProperty code;
	
	@TReaderHtml
	@TLabel(text="#{label.title}")
	@TTextField(maxLength=60, required = true)
	private SimpleStringProperty title;

	@TReaderHtml
	@TLabel(text="#{label.type}")
	@TComboBoxField(firstItemTex="#{label.select}",
	optionsList=@TOptionsList(serviceName = IDocumentTypeController.JNDI_NAME, 
	optionModelViewClass=DocumentTypeMV.class,
	entityClass=DocumentType.class))
	private SimpleObjectProperty<DocumentType> type;
	
	@TReaderHtml
	@TLabel(text="#{label.state}")
	@TComboBoxField(firstItemTex="#{label.select}",
	optionsList=@TOptionsList(serviceName = IDocumentStateController.JNDI_NAME, 
	optionModelViewClass=DocumentStateMV.class,
	entityClass=DocumentState.class))
	private SimpleObjectProperty<DocumentState> state;
	
	@TReaderHtml
	@TLabel(text="#{label.contacts}")
	@TEditEntityModal(modelClass = Contact.class, modelViewClass=ContactMV.class)
	@TModelViewType(modelClass = Contact.class, modelViewClass=ContactMV.class)
	private ITObservableList<ContactMV> contacts;

	@TReaderHtml
	@TLabel(text="#{label.summary}")
	@TTextAreaField(maxLength=250)
	@TVBox(	pane=@TPane(children={"summary","observation"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="summary", priority=Priority.ALWAYS), 
						@TPriority(field="observation", priority=Priority.ALWAYS)}))
	private SimpleStringProperty summary;

	@TReaderHtml
	@TLabel(text="#{label.observation}")
	@TTextAreaField(maxLength=250)
	private SimpleStringProperty observation;
	
	@TLabel(text="#{label.file}")
	@TFileField(propertyValueType=TFileModelType.ENTITY, 
	extensions= {TFileExtension.ALL_FILES}, showFilePath=true)
	private TSimpleFileProperty<TFileEntity> file;
	
	@TFieldBox(node=@TNode(id="evdtl", parse = true))
	@TDetailReaderHtml(label=@TLabel(text="#{label.events}"), entityClass=DocumentEvent.class, modelViewClass=DocumentEventMV.class)
	@TDetailListField(entityModelViewClass = DocumentEventMV.class, entityClass = DocumentEvent.class)
	@TModelViewType(modelClass=DocumentEvent.class, modelViewClass=DocumentEventMV.class)
	private ITObservableList<DocumentEventMV> events;
	
	@TReaderHtml
	@THTMLEditor
	private SimpleStringProperty content;
	
	public DocumentMV(Document entity) {
		super(entity);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getHeader() {
		return header;
	}

	public void setHeader(SimpleStringProperty header) {
		this.header = header;
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
		return title;
	}


}

/**
 * 
 */
package com.tedros.docs.export;

import com.tedros.common.model.TFileEntity;
import com.tedros.docs.ejb.controller.IDocumentStateController;
import com.tedros.docs.ejb.controller.IDocumentTypeController;
import com.tedros.docs.model.Document;
import com.tedros.docs.model.DocumentState;
import com.tedros.docs.model.DocumentType;
import com.tedros.docs.module.model.DocumentStateMV;
import com.tedros.docs.module.model.DocumentTypeMV;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TContent;
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
import com.tedros.fxapi.annotation.presenter.TDetailListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.domain.TFileExtension;
import com.tedros.fxapi.domain.TFileModelType;
import com.tedros.fxapi.presenter.entity.behavior.TDetailCrudViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TDetailCrudViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.fxapi.property.TSimpleFileProperty;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "#{form.doc}", showBreadcrumBar=true, scroll=true)
@TDetailListViewPresenter(presenter=@TPresenter(
behavior = @TBehavior(type = TDetailCrudViewBehavior.class), 
decorator = @TDecorator(type = TDetailCrudViewDecorator.class, 
buildModesRadioButton=false, viewTitle="#{view.docs}")))
public class DetailDocumentMV extends TEntityModelView<Document> {
	

	private SimpleStringProperty displayProperty;
	
	@TTabPane(tabs = { 
		@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"text"})), text = "#{label.main.data}"), 
		@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"content"})), text = "#{label.content}")
	})
	private SimpleLongProperty id;
	
	
	@THBox(	pane=@TPane(children={"code", "file"}), spacing=10, fillHeight=true,
			hgrow=@THGrow(priority={@TPriority(field="code", priority=Priority.SOMETIMES), 
					@TPriority(field="file", priority=Priority.ALWAYS)}))
	private SimpleStringProperty text;
	
	@TLabel(text="#{label.ref.code}")
	@TTextField(maxLength=10,  node=@TNode(requestFocus=true, parse = true))
	@TVBox(	pane=@TPane(children={"code","title","type","state"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="title", priority=Priority.ALWAYS), 
			@TPriority(field="code", priority=Priority.ALWAYS),
			@TPriority(field="type", priority=Priority.ALWAYS),
			@TPriority(field="state", priority=Priority.ALWAYS)}))
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

	@TLabel(text="#{label.file}")
	@TFileField(propertyValueType=TFileModelType.ENTITY, preLoadFileBytes=true,
	extensions= {TFileExtension.ALL_FILES}, showFilePath=true)
	@TVBox(	pane=@TPane(children={"summary","file"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="summary", priority=Priority.ALWAYS), 
						@TPriority(field="file", priority=Priority.ALWAYS)}))
	@TModelViewType(modelClass=TFileEntity.class)
	private TSimpleFileProperty<TFileEntity> file;
	
	@TLabel(text="#{label.summary}")
	@TTextAreaField(maxLength=600, wrapText=true, prefRowCount=10)
	@THBox(	pane=@TPane(children={"summary","observation"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="summary", priority=Priority.ALWAYS), 
						@TPriority(field="observation", priority=Priority.ALWAYS)}))
	private SimpleStringProperty summary;

	@TLabel(text="#{label.observation}")
	@TTextAreaField(maxLength=400, wrapText=true, prefRowCount=10)
	private SimpleStringProperty observation;
	
	
	@THTMLEditor(control=@TControl( maxHeight=500, parse = true))
	private SimpleStringProperty content;
	
	public DetailDocumentMV(Document entity) {
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

	public void setDisplayProperty(SimpleStringProperty displayProperty) {
		this.displayProperty = displayProperty;
	}

}

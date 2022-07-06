/**
 * 
 */
package com.tedros.docs.export;

import com.tedros.common.model.TFileEntity;
import com.tedros.docs.ejb.controller.IDocumentController;
import com.tedros.docs.ejb.controller.IDocumentStateController;
import com.tedros.docs.ejb.controller.IDocumentTypeController;
import com.tedros.docs.model.Document;
import com.tedros.docs.model.DocumentState;
import com.tedros.docs.model.DocumentType;
import com.tedros.docs.module.model.DocumentStateMV;
import com.tedros.docs.module.model.DocumentTypeMV;
import com.tedros.fxapi.TUsualKey;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TFileField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TDetailForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TEditModalPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.domain.TFileExtension;
import com.tedros.fxapi.domain.TFileModelType;
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
@TEditModalPresenter()
@TEjbService(model = Document.class, serviceName = IDocumentController.JNDI_NAME)
public class ModalDocumentMV extends TEntityModelView<Document> {
	

	private SimpleStringProperty displayProperty;

	@TTabPane(tabs = { 
		@TTab(closable=false, 
			content = @TContent(detailForm=@TDetailForm(fields={"code","value", "type", "file"})), text = TUsualKey.MAIN_DATA),
		@TTab(closable=false, content = @TContent(detailForm=@TDetailForm(fields={"observation"})), text = TUsualKey.OBSERVATION)
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
	@TTextAreaField(wrapText=true, prefRowCount=2)
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
	

	@TLabel(text=TUsualKey.FILE)
	@TFileField(propertyValueType=TFileModelType.ENTITY, preLoadFileBytes=true,
	extensions= {TFileExtension.ALL_FILES}, showFilePath=true)
	@TModelViewType(modelClass=TFileEntity.class)
	private TSimpleFileProperty<TFileEntity> file;
	
	
	@TTextAreaField(wrapText=true)
	private SimpleStringProperty observation;
	

	
	public ModalDocumentMV(Document entity) {
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

	@Override
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

	public SimpleStringProperty getValue() {
		return value;
	}

	public void setValue(SimpleStringProperty value) {
		this.value = value;
	}

}

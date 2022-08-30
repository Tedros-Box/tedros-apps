/**
 * 
 */
package org.tedros.docs.export;

import org.tedros.common.model.TFileEntity;
import org.tedros.docs.ejb.controller.IDocumentController;
import org.tedros.docs.ejb.controller.IDocumentStateController;
import org.tedros.docs.ejb.controller.IDocumentTypeController;
import org.tedros.docs.model.Document;
import org.tedros.docs.model.DocumentState;
import org.tedros.docs.model.DocumentType;
import org.tedros.docs.module.model.DocumentStateMV;
import org.tedros.docs.module.model.DocumentTypeMV;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TContent;
import org.tedros.fx.annotation.control.TFileField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.control.TOptionsList;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TDetailForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TEditModalPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.domain.TFileExtension;
import org.tedros.fx.domain.TFileModelType;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.fx.property.TSimpleFileProperty;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TEditModalPresenter(listViewMaxWidth=150, listViewMinWidth=150)
@TEjbService(model = Document.class, serviceName = IDocumentController.JNDI_NAME)
public class ModalDocumentMV extends TEntityModelView<Document> {
	
	@TTabPane(tabs = { 
		@TTab(closable=false, scroll=false, text = TUsualKey.MAIN_DATA,
			content = @TContent(detailForm=@TDetailForm( fields={"code","value", "type", "file"}))),
		@TTab(closable=false, text = TUsualKey.OBSERVATION, 
			content = @TContent(detailForm=@TDetailForm(fields={"observation"})))})
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
	@TComboBoxField(
	optionsList=@TOptionsList(serviceName = IDocumentTypeController.JNDI_NAME, 
	optionModelViewClass=DocumentTypeMV.class,
	entityClass=DocumentType.class))
	@THBox(	pane=@TPane(children={"type","state"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={
			@TPriority(field="type", priority=Priority.NEVER),
			@TPriority(field="state", priority=Priority.NEVER)}))
	private SimpleObjectProperty<DocumentType> type;
	
	@TLabel(text=TUsualKey.STATE)
	@TComboBoxField(
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
		super.formatToString("%s %s", code, name);
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


	public SimpleLongProperty getId() {
		return id;
	}


	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

}

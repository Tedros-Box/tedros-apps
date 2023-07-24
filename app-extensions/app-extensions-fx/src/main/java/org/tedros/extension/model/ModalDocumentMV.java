/**
 * 
 */
package org.tedros.extension.model;

import java.util.Date;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.model.TFormatter;
import org.tedros.extension.ejb.controller.IDocumentController;
import org.tedros.extension.ejb.controller.IExtensionDomainController;
import org.tedros.extension.module.doc.model.DocumentStatusMV;
import org.tedros.extension.module.doc.model.DocumentTypeMV;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TFileField;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TProcess;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TEditModalPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.domain.TFileExtension;
import org.tedros.fx.domain.TFileModelType;
import org.tedros.fx.model.TEntityModelView;
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
				fields={"code","value", "type", "file"}),
		@TTab(closable=false, text = TUsualKey.OBSERVATION, fields={"observation"})})
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
	process=@TProcess(service = IExtensionDomainController.JNDI_NAME, 
	modelView=DocumentTypeMV.class, query=@TQuery(entity=DocumentType.class)))
	@THBox(	pane=@TPane(children={"type","status"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={
			@TPriority(field="type", priority=Priority.NEVER),
			@TPriority(field="status", priority=Priority.NEVER)}))
	private SimpleObjectProperty<DocumentType> type;
	
	@TLabel(text=TUsualKey.STATUS)
	@TComboBoxField(
	process=@TProcess(service = IExtensionDomainController.JNDI_NAME, 
	modelView=DocumentStatusMV.class, query=@TQuery(entity=DocumentStatus.class)))
	private SimpleObjectProperty<DocumentStatus> status;
	

	@TLabel(text=TUsualKey.FILE)
	@TFileField(propertyValueType=TFileModelType.ENTITY, preLoadFileBytes=true,
	extensions= {TFileExtension.ALL_FILES}, showFilePath=true)
	@TGenericType(model=TFileEntity.class)
	private TSimpleFileProperty<TFileEntity> file;
	
	@TTextAreaField(wrapText=true)
	private SimpleStringProperty observation;
	
	public ModalDocumentMV(Document entity) {
		super(entity);
		if(entity.isNew())
			entity.setInsertDate(new Date());
		super.formatToString(TFormatter.create()
				.add(code, o->o.toString()+" ")
				.add(name, o->o.toString()+" ")
				.add(value, o->o.toString()));
	}

}

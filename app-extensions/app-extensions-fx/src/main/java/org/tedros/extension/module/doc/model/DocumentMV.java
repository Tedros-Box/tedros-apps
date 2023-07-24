/**
 * 
 */
package org.tedros.extension.module.doc.model;

import java.util.Date;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.TLanguage;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.core.model.TFormatter;
import org.tedros.extension.ExtKey;
import org.tedros.extension.domain.DomainApp;
import org.tedros.extension.ejb.controller.IDocumentController;
import org.tedros.extension.ejb.controller.IExtensionDomainController;
import org.tedros.extension.model.Contact;
import org.tedros.extension.model.ContactMV;
import org.tedros.extension.model.Document;
import org.tedros.extension.model.DocumentEvent;
import org.tedros.extension.model.DocumentStatus;
import org.tedros.extension.model.DocumentType;
import org.tedros.extension.module.doc.helper.TNotifyBuilder;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TDetailListField;
import org.tedros.fx.annotation.control.TEditEntityModal;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TFileField;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.THTMLEditor;
import org.tedros.fx.annotation.control.TIntegratedLinkField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TProcess;
import org.tedros.fx.annotation.control.TShowField;
import org.tedros.fx.annotation.control.TShowField.TField;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.page.TPage;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TOrder;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.query.TTemporal;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.domain.TFileExtension;
import org.tedros.fx.domain.TFileModelType;
import org.tedros.fx.domain.TTimeStyle;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.property.TSimpleFileProperty;
import org.tedros.server.query.TCompareOp;
import org.tedros.tools.annotation.TNotifyLink;
import org.tedros.util.TDateUtil;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(header = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = IDocumentController.JNDI_NAME, model=Document.class)
@TListViewPresenter(listViewMinWidth=400, listViewMaxWidth=400,
	page=@TPage(serviceName = IDocumentController.JNDI_NAME,
	query = @TQuery(entity=Document.class, condition= {
			@TCondition(field = "code", operator=TCompareOp.EQUAL, label=TUsualKey.REF_CODE),
			@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME),
			@TCondition(field = "value", operator=TCompareOp.LIKE, label=TUsualKey.ADDITIONAL_DATA),
			@TCondition(field = "integratedViewName", operator=TCompareOp.LIKE, label=TFxKey.INTEGRATED_BY),
			@TCondition(field = "insertDate", operator=TCompareOp.GREATER_EQ_THAN, label=TUsualKey.DATE_INSERT, 
			temporal=TTemporal.DATE)},
		orderBy= {@TOrder(label = TUsualKey.REF_CODE, field = "code"),
				@TOrder(label = TUsualKey.NAME, field = "name"),
				@TOrder(label = TUsualKey.ADDITIONAL_DATA, field = "value"),
				@TOrder(label = TFxKey.INTEGRATED_BY, field = "integratedViewName"),
				@TOrder(label = TUsualKey.DATE_INSERT, field = "insertDate")}
			),showSearch=true, showOrderBy=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=ExtKey.VIEW_DOCS)))
@TSecurity(id=DomainApp.DOCUMENT_FORM_ID, 
appName = ExtKey.APP_EXTS, moduleName = ExtKey.MODULE_DOCS, viewName = ExtKey.VIEW_DOCS,
allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
		TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class DocumentMV extends TEntityModelView<Document> {
	
	@TTabPane(tabs = { 
		@TTab(fields={"code","value", "file", "insertDate", "integratedViewName"}, 
				text = TUsualKey.MAIN_DATA), 
		@TTab(fields={"content"}, text = TUsualKey.CONTENT), 
		@TTab(fields={"observation"}, text = TUsualKey.OBSERVATION), 
		@TTab(fields={"events"}, text = TUsualKey.EVENTS)
	})
	private SimpleLongProperty id;
	
	@TLabel(text=TUsualKey.REF_CODE)
	@TTextField(maxLength=10,  node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"code","name","type","status"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS), 
			@TPriority(field="code", priority=Priority.NEVER), 
			@TPriority(field="type", priority=Priority.NEVER), 
			@TPriority(field="status", priority=Priority.NEVER)}))
	private SimpleStringProperty code;
	
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=120, required = true, 
	node=@TNode(requestFocus=true, parse = true) )
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.TYPE)
	@TComboBoxField(
	process=@TProcess(service = IExtensionDomainController.JNDI_NAME, 
	modelView=DocumentTypeMV.class, query=@TQuery(entity=DocumentType.class)))
	private SimpleObjectProperty<DocumentType> type;
	
	@TLabel(text=TUsualKey.STATE)
	@TComboBoxField(
	process=@TProcess(service = IExtensionDomainController.JNDI_NAME, 
	modelView=DocumentStatusMV.class, query=@TQuery(entity=DocumentStatus.class)))
	private SimpleObjectProperty<DocumentStatus> status;
	
	@TLabel(text=TUsualKey.ADDITIONAL_DATA)
	@TTextAreaField(wrapText=true, prefRowCount=4)
	@THBox(	pane=@TPane(children={"value","contacts"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="contacts", priority=Priority.NEVER), 
			@TPriority(field="value", priority=Priority.ALWAYS)}))
	private SimpleStringProperty value;

	@TLabel(text=TUsualKey.CONTACTS)
	@TEditEntityModal(height=100, model = Contact.class, modelView=ContactMV.class)
	@TGenericType(model = Contact.class, modelView=ContactMV.class)
	private ITObservableList<ContactMV> contacts;
	
	@TLabel(text=TUsualKey.FILE)
	@TFileField(propertyValueType=TFileModelType.ENTITY, preLoadFileBytes=true,
	extensions= {TFileExtension.ALL_FILES}, showFilePath=true)
	@TGenericType(model=TFileEntity.class)
	private TSimpleFileProperty<TFileEntity> file;
	
	@TLabel(text=TUsualKey.DATE_INSERT)
	@TShowField(fields= {@TField(format=TDateUtil.DDMMYYYY_HHMM)})
	@THBox(	pane=@TPane(children={"insertDate","notification"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="insertDate", priority=Priority.NEVER),
						@TPriority(field="notification", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<Date> insertDate;
	
	@TNotifyLink(entityBuilder = TNotifyBuilder.class)
	private SimpleStringProperty notification;
	
	@TLabel(text=TFxKey.INTEGRATED_BY)
	@TShowField()
	@THBox(pane=@TPane(children={"integratedViewName", "integratedDate", "integratedModulePath"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="integratedViewName", priority=Priority.SOMETIMES), 
			@TPriority(field="integratedDate", priority=Priority.ALWAYS), 
			@TPriority(field="integratedModulePath", priority=Priority.ALWAYS)}))
	private SimpleStringProperty integratedViewName;
	
	@TLabel(text=TFxKey.INTEGRATED_DATE)
	@TShowField(fields= {@TField(timeStyle=TTimeStyle.SHORT)})
	private SimpleObjectProperty<Date> integratedDate;
	
	@TLabel(text=TFxKey.INTEGRATED_LINK)
	@TIntegratedLinkField
	private SimpleStringProperty integratedModulePath;
	
	@TTextAreaField(wrapText=true)
	private SimpleStringProperty observation;
	
	@TFieldBox(node=@TNode(id="evdtl", parse = true))
	@TDetailListField(modelView = DocumentEventMV.class, entity = DocumentEvent.class)
	@TGenericType(model=DocumentEvent.class, modelView=DocumentEventMV.class)
	private ITObservableList<DocumentEventMV> events;
	
	@THTMLEditor(showActionsToolBar=true,
			control=@TControl( maxHeight=500, parse = true))
	private SimpleStringProperty content;
	
	public DocumentMV(Document entity) {
		super(entity);
		super.formatToString(TFormatter.create()
				.add(name, o->o.toString()+" ")
				.add(integratedViewName, o->TFxKey.INTEGRATED_BY+" "+o.toString()+" ")
				.add(insertDate, 
					o->TUsualKey.ON+" "+TDateUtil.formatMediumDate((Date)o, TLanguage.getLocale())+" ")
				.add(value, o->"| "+o.toString()+" ")
				.add(code, o->"| "+o.toString()));
	}
}

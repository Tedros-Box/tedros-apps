/**
 * 
 */
package org.tedros.services.module.service.model;

import java.util.Date;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.extension.model.Document;
import org.tedros.extension.model.ModalDocumentMV;
import org.tedros.extension.module.doc.trigger.DocumentTrigger;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TConverter;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TEditEntityModal;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.THRadioGroup;
import org.tedros.fx.annotation.control.THTMLEditor;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TRadio;
import org.tedros.fx.annotation.control.TShowField;
import org.tedros.fx.annotation.control.TShowField.TField;
import org.tedros.fx.annotation.control.TSingleSelectionModal;
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
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.domain.TTimeStyle;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.person.model.FindPersonMV;
import org.tedros.person.model.Person;
import org.tedros.server.query.TCompareOp;
import org.tedros.services.ServKey;
import org.tedros.services.converter.StatusConverter;
import org.tedros.services.domain.DomainApp;
import org.tedros.services.domain.Status;
import org.tedros.services.ejb.controller.IContractController;
import org.tedros.services.model.Contract;
import org.tedros.services.model.ContractualAgreement;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(header = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = IContractController.JNDI_NAME, model=Contract.class)
@TListViewPresenter(
	page=@TPage(serviceName = IContractController.JNDI_NAME,
		query = @TQuery(entity=Contract.class, 
			condition= {@TCondition(field = "code", operator=TCompareOp.EQUAL, label=TUsualKey.REF_CODE),
				@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME)},
			orderBy= {@TOrder(label = TUsualKey.NAME, field = "name"),
				@TOrder(label = TUsualKey.REF_CODE , field = "code")}
		),showSearch=true, showOrderBy=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=ServKey.VIEW_CONTRACT,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.CONTRACT_FORM_ID, appName = ServKey.APP_SERVICE,
	moduleName = ServKey.MODULE_SERVICES, viewName = ServKey.VIEW_CONTRACT,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT,
		TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class ContractMV extends TEntityModelView<Contract> {

	@TTabPane(tabs = { 
			@TTab(closable=false, text = TUsualKey.MAIN_DATA, scroll=false,
				fields={"code","description", "contractor","status"}), 
			@TTab(closable=false, text = TUsualKey.CONTENT, fields={"content"}),  
			@TTab(closable=false, text = TUsualKey.OBSERVATION, fields={"observation"})
		})
	private SimpleLongProperty id;
	
	@TLabel(text=TUsualKey.REF_CODE)
	@TTextField(maxLength=15)
	@THBox(	pane=@TPane(children={"code","name","beginDate", "endDate"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS), 
			@TPriority(field="code", priority=Priority.NEVER),
			@TPriority(field="beginDate", priority=Priority.NEVER), 
			@TPriority(field="endDate", priority=Priority.NEVER)}))
	private SimpleStringProperty code;

	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=250, required = true, 
	node=@TNode(requestFocus=true, parse = true) )
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(maxLength=2000, wrapText=true, prefRowCount=4)
	private SimpleStringProperty description;

	@TLabel(text=TUsualKey.BEGIN_DATE)
	@TDatePickerField
	private SimpleObjectProperty<Date> beginDate;
	
	@TLabel(text=TUsualKey.END_DATE)
	@TDatePickerField
	private SimpleObjectProperty<Date> endDate;
	
	@TLabel(text=TUsualKey.CONTRACTOR)
	@TSingleSelectionModal(height=80,
	model = Person.class, modelView = FindPersonMV.class)
	@THBox(	pane=@TPane(children={"contractor","contracted", "agreements", "documents"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="contractor", priority=Priority.NEVER), 
			@TPriority(field="contracted", priority=Priority.NEVER), 
			@TPriority(field="agreements", priority=Priority.NEVER), 
			@TPriority(field="documents", priority=Priority.NEVER)}))
	public SimpleObjectProperty<FindPersonMV> contractor;
	
	@TLabel(text=TUsualKey.CONTRACTED)
	@TSingleSelectionModal(height=80,
	model = Person.class, modelView = FindPersonMV.class)
	public SimpleObjectProperty<FindPersonMV> contracted;
	
	@TLabel(text=TUsualKey.AGREEMENTS)
	@TEditEntityModal(modalHeight=400, modalWidth=600, height=80,
	model = ContractualAgreement.class, modelView=ContractualAgreementMV.class)
	@TGenericType(model = ContractualAgreement.class, modelView=ContractualAgreementMV.class)
	private ITObservableList<ContractualAgreementMV> agreements;
	
	@TLabel(text=TUsualKey.DOCUMENTS)
	@TEditEntityModal(modalHeight=490, modalWidth=700, height=80,
	model = Document.class, modelView=ModalDocumentMV.class)
	@TTrigger(type = DocumentTrigger.class)
	@TGenericType(model=Document.class, modelView=ModalDocumentMV.class)
	public ITObservableList<ModalDocumentMV> documents;
	
	@TLabel(text=TUsualKey.STATUS)
	@THRadioGroup(spacing= 10,
	converter=@TConverter(parse = true, type = StatusConverter.class),
	radio = { @TRadio(text = TUsualKey.ENABLED, userData = TUsualKey.ENABLED),
			@TRadio(text = TUsualKey.DISABLED, userData = TUsualKey.DISABLED),
			@TRadio(text = TUsualKey.SUSPENDED, userData = TUsualKey.SUSPENDED)
	})
	@THBox(	pane=@TPane(children={"status","insertDate","lastUpdate"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="insertDate", priority=Priority.NEVER), 
						@TPriority(field="lastUpdate", priority=Priority.NEVER), 
						@TPriority(field="status", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<Status> status;
	
	@TLabel(text=TUsualKey.DATE_INSERT)
	@TShowField(fields= {@TField(timeStyle=TTimeStyle.SHORT)})
	private SimpleObjectProperty<Date> insertDate;
	
	@TLabel(text=TUsualKey.DATE_UPDATE)
	@TShowField(fields= {@TField(timeStyle=TTimeStyle.SHORT)})
	private SimpleObjectProperty<Date> lastUpdate;
	
	
	@TTextAreaField(maxLength=2000, wrapText=true)
	private SimpleStringProperty observation;
	
	@THTMLEditor(showActionsToolBar=true,
			control=@TControl( maxHeight=500, parse = true))
	private SimpleStringProperty content;
	
	public ContractMV(Contract entity) {
		super(entity);
		super.formatToString("%s %s", code, name);
	}


}

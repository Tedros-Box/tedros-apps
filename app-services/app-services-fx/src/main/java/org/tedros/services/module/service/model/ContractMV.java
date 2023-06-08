/**
 * 
 */
package org.tedros.services.module.service.model;

import java.util.Date;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.extension.model.Document;
import org.tedros.extension.model.ModalDocumentMV;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TContent;
import org.tedros.fx.annotation.control.TConverter;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TEditEntityModal;
import org.tedros.fx.annotation.control.THTMLEditor;
import org.tedros.fx.annotation.control.THorizontalRadioGroup;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.control.TOneSelectionModal;
import org.tedros.fx.annotation.control.TRadioButton;
import org.tedros.fx.annotation.control.TShowField;
import org.tedros.fx.annotation.control.TShowField.TField;
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
import org.tedros.fx.presenter.model.TEntityModelView;
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
import org.tedros.util.TDateUtil;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "", showBreadcrumBar=false, scroll=true)
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
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class ContractMV extends TEntityModelView<Contract> {

	@TTabPane(tabs = { 
			@TTab(closable=false, text = TUsualKey.MAIN_DATA, scroll=false,
				content = @TContent(detailForm=@TDetailForm(
						fields={"code","description", "contractor","status"}))), 
			@TTab(closable=false, text = TUsualKey.CONTENT, 
				content = @TContent(detailForm=@TDetailForm(fields={"content"}))),  
			@TTab(closable=false, text = TUsualKey.OBSERVATION, 
				content = @TContent(detailForm=@TDetailForm(fields={"observation"})))
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
	@TOneSelectionModal(height=80,
	modelClass = Person.class, modelViewClass = FindPersonMV.class)
	@THBox(	pane=@TPane(children={"contractor","contracted", "agreements", "documents"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="contractor", priority=Priority.NEVER), 
			@TPriority(field="contracted", priority=Priority.NEVER), 
			@TPriority(field="agreements", priority=Priority.NEVER), 
			@TPriority(field="documents", priority=Priority.NEVER)}))
	public SimpleObjectProperty<FindPersonMV> contractor;
	
	@TLabel(text=TUsualKey.CONTRACTED)
	@TOneSelectionModal(height=80,
	modelClass = Person.class, modelViewClass = FindPersonMV.class)
	public SimpleObjectProperty<FindPersonMV> contracted;
	
	@TLabel(text=TUsualKey.AGREEMENTS)
	@TEditEntityModal(modalHeight=400, modalWidth=600, height=80,
	modelClass = ContractualAgreement.class, modelViewClass=ContractualAgreementMV.class)
	@TModelViewType(modelClass = ContractualAgreement.class, modelViewClass=ContractualAgreementMV.class)
	private ITObservableList<ContractualAgreementMV> agreements;
	
	@TLabel(text=TUsualKey.DOCUMENTS)
	@TEditEntityModal(modalHeight=490, modalWidth=700, height=80,
	modelClass = Document.class, modelViewClass=ModalDocumentMV.class)
	@TModelViewType(modelClass=Document.class, modelViewClass=ModalDocumentMV.class)
	public ITObservableList<ModalDocumentMV> documents;
	
	@TLabel(text=TUsualKey.STATUS)
	@THorizontalRadioGroup(spacing= 10,
	converter=@TConverter(parse = true, type = StatusConverter.class),
	radioButtons = { @TRadioButton(text = TUsualKey.ENABLED, userData = TUsualKey.ENABLED),
			@TRadioButton(text = TUsualKey.DISABLED, userData = TUsualKey.DISABLED),
			@TRadioButton(text = TUsualKey.SUSPENDED, userData = TUsualKey.SUSPENDED)
	})
	@THBox(	pane=@TPane(children={"status","insertDate","lastUpdate"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="insertDate", priority=Priority.NEVER), 
						@TPriority(field="lastUpdate", priority=Priority.NEVER), 
						@TPriority(field="status", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<Status> status;
	
	@TLabel(text=TUsualKey.DATE_INSERT)
	@TShowField(fields= {@TField(pattern=TDateUtil.DDMMYYYY_HHMM)})
	private SimpleObjectProperty<Date> insertDate;
	
	@TLabel(text=TUsualKey.DATE_UPDATE)
	@TShowField(fields= {@TField(pattern=TDateUtil.DDMMYYYY_HHMM)})
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

	public SimpleStringProperty getDescription() {
		return description;
	}

	public void setDescription(SimpleStringProperty description) {
		this.description = description;
	}

	public SimpleObjectProperty<FindPersonMV> getContractor() {
		return contractor;
	}

	public void setContractor(SimpleObjectProperty<FindPersonMV> contractor) {
		this.contractor = contractor;
	}

	public SimpleObjectProperty<Date> getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(SimpleObjectProperty<Date> beginDate) {
		this.beginDate = beginDate;
	}

	public SimpleObjectProperty<Date> getEndDate() {
		return endDate;
	}

	public void setEndDate(SimpleObjectProperty<Date> endDate) {
		this.endDate = endDate;
	}

	public ITObservableList<ContractualAgreementMV> getAgreements() {
		return agreements;
	}

	public void setAgreements(ITObservableList<ContractualAgreementMV> agreements) {
		this.agreements = agreements;
	}

	public ITObservableList<ModalDocumentMV> getDocuments() {
		return documents;
	}

	public void setDocuments(ITObservableList<ModalDocumentMV> documents) {
		this.documents = documents;
	}

	public SimpleObjectProperty<Status> getStatus() {
		return status;
	}

	public void setStatus(SimpleObjectProperty<Status> status) {
		this.status = status;
	}

	public SimpleStringProperty getObservation() {
		return observation;
	}

	public void setObservation(SimpleStringProperty observation) {
		this.observation = observation;
	}

	public SimpleObjectProperty<FindPersonMV> getContracted() {
		return contracted;
	}

	public void setContracted(SimpleObjectProperty<FindPersonMV> contracted) {
		this.contracted = contracted;
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

	public SimpleStringProperty getContent() {
		return content;
	}

	public void setContent(SimpleStringProperty content) {
		this.content = content;
	}

}

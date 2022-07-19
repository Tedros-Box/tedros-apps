/**
 * 
 */
package com.tedros.services.module.service.model;

import java.util.Date;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.docs.export.ModalDocumentMV;
import com.tedros.docs.model.Document;
import com.tedros.fxapi.TUsualKey;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TConverter;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.TEditEntityModal;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.annotation.control.TOneSelectionModal;
import com.tedros.fxapi.annotation.control.TRadioButton;
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
import com.tedros.fxapi.annotation.view.TOption;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.person.model.FindPersonMV;
import com.tedros.person.model.Person;
import com.tedros.services.ServKey;
import com.tedros.services.converter.StatusConverter;
import com.tedros.services.domain.DomainApp;
import com.tedros.services.domain.Status;
import com.tedros.services.ejb.controller.IContractController;
import com.tedros.services.model.Contract;
import com.tedros.services.model.ContractualAgreement;

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
	paginator=@TPaginator(entityClass = Contract.class, serviceName = IContractController.JNDI_NAME,
		show=true, showSearchField=true, searchFieldName="name", 
		orderBy = {	@TOption(text = TUsualKey.NAME , value = "name"),
				@TOption(text = TUsualKey.REF_CODE , value = "code")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=ServKey.VIEW_CONTRACT,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.CONTRACT_FORM_ID, appName = ServKey.APP_SERVICE,
	moduleName = ServKey.MODULE_SERVICES, viewName = ServKey.VIEW_CONTRACT,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class ContractMV extends TEntityModelView<Contract> {

	private SimpleLongProperty id;
	
	@TTabPane(tabs = { 
			@TTab(closable=false, text = TUsualKey.MAIN_DATA, scroll=false,
				content = @TContent(detailForm=@TDetailForm(
						fields={"code","description","beginDate", "contractor"}))),  
			@TTab(closable=false, text = TUsualKey.OBSERVATION, 
				content = @TContent(detailForm=@TDetailForm(fields={"observation"})))
		})
	private SimpleStringProperty displayProperty;
	
	@TLabel(text=TUsualKey.REF_CODE)
	@TTextField(maxLength=15)
	@THBox(	pane=@TPane(children={"code","name"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS), 
			@TPriority(field="code", priority=Priority.NEVER)}))
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
	@THBox(	pane=@TPane(children={"beginDate", "endDate", "status"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="beginDate", priority=Priority.NEVER), 
			@TPriority(field="endDate", priority=Priority.NEVER), 
			@TPriority(field="status", priority=Priority.NEVER)}))
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
	private SimpleObjectProperty<Status> status;
	
	@TTextAreaField(maxLength=2000, wrapText=true)
	private SimpleStringProperty observation;
	
	public ContractMV(Contract entity) {
		super(entity);
		super.formatFieldsToDisplay("%s %s", code, name);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getDisplayProperty() {
		return displayProperty;
	}

	public void setDisplayProperty(SimpleStringProperty displayProperty) {
		this.displayProperty = displayProperty;
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

}

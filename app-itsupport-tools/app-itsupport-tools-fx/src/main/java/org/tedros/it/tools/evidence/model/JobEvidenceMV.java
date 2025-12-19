package org.tedros.it.tools.evidence.model;

import java.util.Date;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.THTMLEditor;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.control.TTrigger;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.form.TSetting;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TVBox;
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
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.ejb.controller.IJobEvidenceController;
import org.tedros.it.tools.entity.JobEvidence;
import org.tedros.it.tools.entity.JobEvidenceItem;
import org.tedros.it.tools.evidence.setting.JobEvidenceSettings;
import org.tedros.it.tools.evidence.trigger.SearchForIssueTrigger;
import org.tedros.person.ejb.controller.IEmployeeController;
import org.tedros.person.model.Employee;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TLogicOp;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

@TSetting(JobEvidenceSettings.class)
@TForm(header = "", showBreadcrumBar=false, scroll=false)
@TEjbService(serviceName = IJobEvidenceController.JNDI_NAME, model=JobEvidence.class, filterByLoggedUser=true)
@TListViewPresenter(
		page=@TPage(serviceName = IJobEvidenceController.JNDI_NAME,
			filterByLoggedUser=true,
			query = @TQuery(entity=JobEvidence.class, condition= {
				@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME),
				@TCondition(field = "issueNumber", operator=TCompareOp.EQUAL, label=ItToolsKey.ISSUE_NUMBER)
				},
				orderBy= {@TOrder(label = ItToolsKey.ISSUE_NUMBER, field = "issueNumber"),
						@TOrder(label = TUsualKey.NAME, field = "name")}
					),showSearch=true, showOrderBy=true),
		presenter=@TPresenter(
			decorator = @TDecorator(viewTitle=ItToolsKey.VIEW_JOB_EVIDENCE, buildModesRadioButton=false),
			behavior=@TBehavior(runNewActionAfterSave=false, saveAllModels=false, saveOnlyChangedModels=false)))

@TSecurity(id=DomainApp.EVIDENCE_MANAGER_FORM_ID, appName = ItToolsKey.APP_ITSUPPORT,
	moduleName = ItToolsKey.MODULE_ITSUPPORT_EVIDENCE, viewName = ItToolsKey.VIEW_JOB_EVIDENCE,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class JobEvidenceMV extends TEntityModelView<JobEvidence> {
	
	@TTabPane(tabs = { 
		@TTab(text = TUsualKey.MAIN_DATA, 	fields={"issueNumber"}), 
		@TTab(text = TUsualKey.DESCRIPTION,	fields={"description"}),
		@TTab(text = ItToolsKey.EVIDENCES, 	fields={"itemsHeader"})})
	private SimpleLongProperty id;

	
	@TLabel(text = ItToolsKey.ISSUE_NUMBER)
	@TTextField(maxLength=50, required = true)
	@TFlowPane(hgap=20, vgap=12,
		pane=@TPane(children={"issueNumber", "name", "tool", "employee", "executionDate", "issueTitle"}))	
	@TTrigger(type = SearchForIssueTrigger.class, targetFieldName = "issueTitle", associatedFieldBox = "issueLink")
    private SimpleStringProperty issueNumber;
	
	@TLabel(text = TUsualKey.NAME)
	@TTextField(maxLength=120, required = true)
    private SimpleStringProperty name;
	
	@TLabel(text = ItToolsKey.TOOL)
	@TTextField(maxLength=50)	
    private SimpleStringProperty tool;

	@THBox(pane=@TPane(children={"issueTitle", "issueLink"}))
	@TLabel(text = TUsualKey.TITLE)
	@TTextAreaField(maxLength=500, wrapText=true)
    private SimpleStringProperty issueTitle;

	@TLabel(text = ItToolsKey.ISSUE_LINK)
	@TTextAreaField(maxLength=2083, wrapText=true)
    private SimpleStringProperty issueLink;

	@TLabel(text=TUsualKey.EMPLOYEE)
	@TAutoCompleteEntity(required = true,
			service = IEmployeeController.JNDI_NAME,
			query = @TQuery(entity = Employee.class, 
			condition = {
					@TCondition(field = "name", operator=TCompareOp.LIKE), 
					@TCondition(logicOp=TLogicOp.OR, field = "lastName", operator=TCompareOp.LIKE)}))
    private SimpleObjectProperty<Employee> employee;

	@TLabel(text=ItToolsKey.EXECUTION_DATE)
	@TDatePickerField()
    private SimpleObjectProperty<Date> executionDate;
    
    @THTMLEditor(control=@TControl( maxHeight=450, parse = true))
    private SimpleStringProperty description;

    @TVBox(pane=@TPane(children={"itemsHeader"}))
    @TText(textStyle = TTextStyle.LARGE, text=ItToolsKey.TEXT_SELECT_EVIDENCES)
    @TFieldBox(node=@TNode(parse = true, id=TFieldBox.TITLE))
	private SimpleStringProperty itemsHeader;
    
    @TGenericType(model = JobEvidenceItem.class)
    private ITObservableList<JobEvidenceItem> items;

    public JobEvidenceMV(JobEvidence entity) {
        super(entity);
        super.formatToString("%s - %s", issueNumber, name);
    }

	@Override
	public SimpleLongProperty getId() {
		return id;
	}
	
	@Override
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getTool() {
		return tool;
	}

	public void setTool(SimpleStringProperty tool) {
		this.tool = tool;
	}

	public SimpleStringProperty getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(SimpleStringProperty issueNumber) {
		this.issueNumber = issueNumber;
	}

	public SimpleStringProperty getIssueTitle() {
		return issueTitle;
	}

	public void setIssueTitle(SimpleStringProperty issueTitle) {
		this.issueTitle = issueTitle;
	}

	public SimpleStringProperty getIssueLink() {
		return issueLink;
	}

	public void setIssueLink(SimpleStringProperty issueLink) {
		this.issueLink = issueLink;
	}

	public SimpleObjectProperty<Employee> getEmployee() {
		return employee;
	}

	public void setEmployee(SimpleObjectProperty<Employee> employee) {
		this.employee = employee;
	}

	public SimpleObjectProperty<Date> getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(SimpleObjectProperty<Date> executionDate) {
		this.executionDate = executionDate;
	}

	public SimpleStringProperty getDescription() {
		return description;
	}

	public void setDescription(SimpleStringProperty description) {
		this.description = description;
	}

	public ITObservableList<JobEvidenceItem> getItems() {
		return items;
	}

	public void setItems(ITObservableList<JobEvidenceItem> items) {
		this.items = items;
	}

	public SimpleStringProperty getItemsHeader() {
		return itemsHeader;
	}

	public void setItemsHeader(SimpleStringProperty itemsHeader) {
		this.itemsHeader = itemsHeader;
	}

}

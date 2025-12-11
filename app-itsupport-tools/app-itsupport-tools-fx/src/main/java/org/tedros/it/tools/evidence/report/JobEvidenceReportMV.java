package org.tedros.it.tools.evidence.report;

import java.util.Date;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TDatePickerField;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TRadio;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTableView.TTableViewSelectionModel;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.control.TVRadioGroup;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.TAccordion;
import org.tedros.fx.annotation.layout.TFieldSet;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TTitledPane;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TReportProcess;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.builder.DateTimeFormatBuilder;
import org.tedros.fx.builder.TRowFactoryWithOpenAndRemoveAction;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.report.behavior.TDataSetReportBehavior;
import org.tedros.fx.presenter.report.decorator.TDataSetReportDecorator;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.evidence.report.process.JobEvidenceReportProcess;
import org.tedros.it.tools.model.JobEvidenceReportItemModel;
import org.tedros.it.tools.model.JobEvidenceReportModel;
import org.tedros.person.ejb.controller.IEmployeeController;
import org.tedros.person.model.Employee;
import org.tedros.person.module.report.action.SearchAction;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TLogicOp;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.SelectionMode;


@TForm(header = "", showBreadcrumBar=false, editCssId="")
@TReportProcess(type=JobEvidenceReportProcess.class, model = JobEvidenceReportModel.class)
@TPresenter(type = TDynaPresenter.class,
	behavior = @TBehavior(type = TDataSetReportBehavior.class, 
		action=SearchAction.class), 
	decorator = @TDecorator(type = TDataSetReportDecorator.class, 
		viewTitle=ItToolsKey.VIEW_JOB_EVIDENCE_REPORT))
@TSecurity(	id=DomainApp.EVIDENCE_REPORT_FORM_ID, 
appName = ItToolsKey.APP_ITSUPPORT, 
moduleName = ItToolsKey.MODULE_ITSUPPORT_EVIDENCE, 
viewName = ItToolsKey.VIEW_JOB_EVIDENCE_REPORT,
allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EXPORT, TAuthorizationType.SEARCH})
public class JobEvidenceReportMV extends TModelView<JobEvidenceReportModel>{
	
	@TAccordion(expandedPane="filtro", node=@TNode(id="repdoaacc",parse = true),
		panes={
			@TTitledPane(text=TUsualKey.FILTERS, node=@TNode(id="filtro",parse = true), 
				expanded=true, 
				fields={"name", "orderBy"}),
			@TTitledPane(text=TUsualKey.RESULT, node=@TNode(id="resultado",parse = true),
				fields={"result"})})	
	private SimpleLongProperty id;
		
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=120)
	@TFlowPane(hgap=20, vgap=12,	
		pane=@TPane(children={"name", "issueNumber", "issueTitle", "executionDate", "employee" }))
	private SimpleStringProperty name;

	@TLabel(text=ItToolsKey.ISSUE_NUMBER)
	@TTextField(maxLength=120)
	private SimpleStringProperty issueNumber;
	
	@TLabel(text=TUsualKey.TITLE)
	@TTextField(maxLength=120)
	private SimpleStringProperty issueTitle;
	
	@TLabel(text=ItToolsKey.EXECUTION_DATE)
	@TDatePickerField(
	dateFormat=DateTimeFormatBuilder.class)
	private SimpleObjectProperty<Date> executionDate;
	
	@TLabel(text=TUsualKey.EMPLOYEE)
	@TAutoCompleteEntity(service = IEmployeeController.JNDI_NAME,
		query = @TQuery(entity = Employee.class, 
		condition = {
			@TCondition(field = "name", operator=TCompareOp.LIKE), 
			@TCondition(logicOp=TLogicOp.OR, field = "lastName", operator=TCompareOp.LIKE)}))
    private SimpleObjectProperty<Employee> employee;	
	
	@TLabel(text=TFxKey.SORT_BY)
	@TFieldSet(fields = { "orderBy", "orderType" }, 
		region=@TRegion(maxWidth=600, parse = true),
		legend =TUsualKey.RESULT_ORDER)
	@TVRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
		radio = {@TRadio(text=TUsualKey.NAME, userData="p.name"),  
					@TRadio(text=ItToolsKey.ISSUE_NUMBER, userData="p.issueNumber")
		})
	private SimpleStringProperty orderBy;
	
	@TLabel(text=TFxKey.SORT_TYPE)
	@TVRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radio = {@TRadio(text=TFxKey.SORT_BY_ASC, userData="asc"), 
					@TRadio(text=TFxKey.SORT_BY_DESC, userData="desc")
	})
	private SimpleStringProperty orderType;
	
	@TTableView(
			selectionModel=@TTableViewSelectionModel(
					selectionMode=SelectionMode.MULTIPLE,parse = true), 
			rowFactory=TRowFactoryWithOpenAndRemoveAction.class,
		control=@TControl(tooltip=TFxKey.TABLE_MENU_TOOLTIP, parse = true),
		columns = { 
				@TTableColumn(cellValue="name", text = TUsualKey.NAME, resizable=true), 
				@TTableColumn(cellValue="issueNumber", text = ItToolsKey.ISSUE_NUMBER, resizable=true), 
				@TTableColumn(cellValue="issueTitle", text = TUsualKey.TITLE, resizable=true), 
				@TTableColumn(cellValue="employee", text = TUsualKey.EMPLOYEE, resizable=true), 
				@TTableColumn(cellValue="executionDate", text = ItToolsKey.EXECUTION_DATE, resizable=true)
				
			})
	@TGenericType(model=JobEvidenceReportItemModel.class, modelView=JobEvidenceTV.class)
	private ITObservableList<JobEvidenceTV> result;
	
	public JobEvidenceReportMV(JobEvidenceReportModel entidade) {
		super(entidade);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
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

	public SimpleObjectProperty<Date> getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(SimpleObjectProperty<Date> executionDate) {
		this.executionDate = executionDate;
	}

	public SimpleObjectProperty<Employee> getEmployee() {
		return employee;
	}

	public void setEmployee(SimpleObjectProperty<Employee> employee) {
		this.employee = employee;
	}

	public SimpleStringProperty getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(SimpleStringProperty orderBy) {
		this.orderBy = orderBy;
	}

	public SimpleStringProperty getOrderType() {
		return orderType;
	}

	public void setOrderType(SimpleStringProperty orderType) {
		this.orderType = orderType;
	}

	public ITObservableList<JobEvidenceTV> getResult() {
		return result;
	}

	public void setResult(ITObservableList<JobEvidenceTV> result) {
		this.result = result;
	}


}

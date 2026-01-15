package org.tedros.it.tools.module.evidence.report;

import org.tedros.fx.model.TModelView;
import org.tedros.it.tools.model.JobEvidenceReportItemModel;

import javafx.beans.property.SimpleStringProperty;

public class JobEvidenceTV extends TModelView<JobEvidenceReportItemModel>{
		
	private SimpleStringProperty name;
	private SimpleStringProperty issueNumber;
	private SimpleStringProperty issueTitle;
	private SimpleStringProperty employee;
	private SimpleStringProperty executionDate;
	
	public JobEvidenceTV(JobEvidenceReportItemModel model) {
		super(model);
		super.formatToString("%s", name);
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

	public SimpleStringProperty getEmployee() {
		return employee;
	}

	public void setEmployee(SimpleStringProperty employee) {
		this.employee = employee;
	}

	public SimpleStringProperty getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(SimpleStringProperty executionDate) {
		this.executionDate = executionDate;
	}
	
	

}

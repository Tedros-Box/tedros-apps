package org.tedros.it.tools.model;

import org.tedros.server.model.TReportModel;

public class JobEvidenceReportModel extends TReportModel<JobEvidenceReportItemModel> {

	private static final long serialVersionUID = 9211915648972400338L;
	
	private String name;
	private String issueNumber;
	private String issueTitle;
	private String employee;
	private String executionDateBegin;
	private String executionDateEnd;
	
	public String getIssueNumber() {
		return issueNumber;
	}
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
	public String getIssueTitle() {
		return issueTitle;
	}
	public void setIssueTitle(String issueTitle) {
		this.issueTitle = issueTitle;
	}
	public String getEmployee() {
		return employee;
	}
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	public String getExecutionDateBegin() {
		return executionDateBegin;
	}
	public void setExecutionDateBegin(String executionDateBegin) {
		this.executionDateBegin = executionDateBegin;
	}
	public String getExecutionDateEnd() {
		return executionDateEnd;
	}
	public void setExecutionDateEnd(String executionDateEnd) {
		this.executionDateEnd = executionDateEnd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}

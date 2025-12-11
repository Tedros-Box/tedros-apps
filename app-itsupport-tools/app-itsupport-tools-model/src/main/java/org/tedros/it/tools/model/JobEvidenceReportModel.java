package org.tedros.it.tools.model;

import java.util.Date;

import org.tedros.person.model.Employee;
import org.tedros.server.model.TReportModel;

public class JobEvidenceReportModel extends TReportModel<JobEvidenceReportItemModel> {

	private static final long serialVersionUID = 9211915648972400338L;
	
	private String name;
	private String issueNumber;
	private String issueTitle;
	private Employee employee;
	private Date executionDate;
	
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
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Date getExecutionDate() {
		return executionDate;
	}
	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}

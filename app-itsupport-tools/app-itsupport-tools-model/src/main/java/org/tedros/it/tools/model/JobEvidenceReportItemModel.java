package org.tedros.it.tools.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.tedros.it.tools.entity.JobEvidence;
import org.tedros.server.model.ITReportItemModel;

public class JobEvidenceReportItemModel implements ITReportItemModel<JobEvidence> {
	
	private static final long serialVersionUID = -5675260170250079744L;
	
	private static final String DDMMYYYY = "dd/MM/yyyy";
	
	private JobEvidence model;
	private String name;
	private String description;
	private String tool;
	private String issueNumber;
	private String issueTitle;
	private String issueLink;
	private String employee;
	private String executionDate;
	private List<JobEvidenceImageItem> items;
	
	public JobEvidenceReportItemModel(JobEvidence model) {
		this.model = model;
		this.name = model.getName();
		this.description = model.getDescription();
		this.tool = model.getTool();
		this.issueNumber = model.getIssueNumber();
		this.issueTitle = model.getIssueTitle();
		this.issueLink = model.getIssueLink();
		String lastName = model.getEmployee().getLastName();
		this.employee = model.getEmployee().getName() + (lastName!=null ? " "+lastName : "");
		this.executionDate = new SimpleDateFormat(DDMMYYYY).format(model.getExecutionDate());
		
		items = new ArrayList<>();
		if(ObjectUtils.isNotEmpty(model.getItems())) {
			items = model.getItems().stream()
			.map(JobEvidenceImageItem::new)
			.toList();
		}
		
	}

	@Override
	public JobEvidence getModel() {
		return model;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTool() {
		return tool;
	}

	public void setTool(String tool) {
		this.tool = tool;
	}

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

	public String getIssueLink() {
		return issueLink;
	}

	public void setIssueLink(String issueLink) {
		this.issueLink = issueLink;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(String executionDate) {
		this.executionDate = executionDate;
	}

	public List<JobEvidenceImageItem> getItems() {
		return items;
	}

	public void setItems(List<JobEvidenceImageItem> items) {
		this.items = items;
	}

	public void setModel(JobEvidence model) {
		this.model = model;
	}

}

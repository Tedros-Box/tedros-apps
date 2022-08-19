/**
 * 
 */
package org.tedros.person.report.model;

import java.util.Date;

import org.tedros.person.model.LegalType;

import org.tedros.server.model.TReportModel;

/**
 * @author Davis Gordon
 *
 */
public class LegalPersonReportModel extends TReportModel<LegalPersonItemModel> {


	private static final long serialVersionUID = -1700268697292732487L;

	private String name;
	
	private String otherName;
	
	private LegalType type;
	
	private Date startActivities;

	private Date endActivities;

	private Date startActivitiesEnd;

	private Date endActivitiesEnd;
	/**
	 * 
	 */
	public LegalPersonReportModel() {
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOtherName() {
		return otherName;
	}
	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
	public LegalType getType() {
		return type;
	}
	public void setType(LegalType type) {
		this.type = type;
	}
	public Date getStartActivities() {
		return startActivities;
	}
	public void setStartActivities(Date startActivities) {
		this.startActivities = startActivities;
	}
	public Date getEndActivities() {
		return endActivities;
	}
	public void setEndActivities(Date endActivities) {
		this.endActivities = endActivities;
	}
	public Date getStartActivitiesEnd() {
		return startActivitiesEnd;
	}
	public void setStartActivitiesEnd(Date startActivitiesEnd) {
		this.startActivitiesEnd = startActivitiesEnd;
	}
	public Date getEndActivitiesEnd() {
		return endActivitiesEnd;
	}
	public void setEndActivitiesEnd(Date endActivitiesEnd) {
		this.endActivitiesEnd = endActivitiesEnd;
	}
}

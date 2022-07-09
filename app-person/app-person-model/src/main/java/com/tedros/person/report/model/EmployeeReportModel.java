/**
 * 
 */
package com.tedros.person.report.model;

import java.util.Date;

import com.tedros.ejb.base.model.TReportModel;
import com.tedros.person.domain.Gender;
import com.tedros.person.domain.Sex;
import com.tedros.person.model.LegalPerson;
import com.tedros.person.model.StaffType;

/**
 * @author Davis Gordon
 *
 */
public class EmployeeReportModel extends TReportModel<EmployeeItemModel> {

	private static final long serialVersionUID = -6868047086745020961L;

	private String name;
	
	private String lastName;
	
	private StaffType type;
	
	private Date birthDate;
	
	private Date birthDateEnd;
	
	private Sex sex;
	
	private Gender gender;
	
	private Date hiringDate;
	

	private Date hiringDateEnd;

	private Date resignationDate;
	
	private Date resignationDateEnd;
	
	private LegalPerson employer;
	/**
	 * 
	 */
	public EmployeeReportModel() {
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public StaffType getType() {
		return type;
	}
	public void setType(StaffType type) {
		this.type = type;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Date getHiringDate() {
		return hiringDate;
	}
	public void setHiringDate(Date hiringDate) {
		this.hiringDate = hiringDate;
	}
	public Date getResignationDate() {
		return resignationDate;
	}
	public void setResignationDate(Date resignationDate) {
		this.resignationDate = resignationDate;
	}
	public LegalPerson getEmployer() {
		return employer;
	}
	public void setEmployer(LegalPerson employer) {
		this.employer = employer;
	}
	public Date getBirthDateEnd() {
		return birthDateEnd;
	}
	public void setBirthDateEnd(Date birthDateEnd) {
		this.birthDateEnd = birthDateEnd;
	}
	public Date getHiringDateEnd() {
		return hiringDateEnd;
	}
	public void setHiringDateEnd(Date hiringDateEnd) {
		this.hiringDateEnd = hiringDateEnd;
	}
	public Date getResignationDateEnd() {
		return resignationDateEnd;
	}
	public void setResignationDateEnd(Date resignationDateEnd) {
		this.resignationDateEnd = resignationDateEnd;
	}
}

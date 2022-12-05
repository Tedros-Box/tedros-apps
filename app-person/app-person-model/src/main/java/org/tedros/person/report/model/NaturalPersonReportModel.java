/**
 * 
 */
package org.tedros.person.report.model;

import java.util.Date;

import org.tedros.person.domain.Gender;
import org.tedros.person.domain.Sex;
import org.tedros.server.model.TReportModel;

/**
 * @author Davis Gordon
 *
 */
public class NaturalPersonReportModel extends TReportModel<NaturalPersonItemModel> {

	private static final long serialVersionUID = -6868047086745020961L;

	private String name;
	
	private String lastName;
	
	private Date birthDate;
	
	private Date birthDateEnd;
	
	private Sex sex;
	
	private Gender gender;
	/**
	 * 
	 */
	public NaturalPersonReportModel() {
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
	public Date getBirthDateEnd() {
		return birthDateEnd;
	}
	public void setBirthDateEnd(Date birthDateEnd) {
		this.birthDateEnd = birthDateEnd;
	}
}

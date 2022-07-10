/**
 * 
 */
package com.tedros.person.report.model;

import java.util.Date;

import com.tedros.person.model.Employee;
import com.tedros.util.TDateUtil;

/**
 * @author Davis Gordon
 *
 */
public class EmployeeModel {
	
	private String name;
	
	private String hiringDate;
	
	private String resignationDate;
	
	private String type;

	public EmployeeModel(Employee p) {
		this.name = p.getName() + (p.getLastName()!=null ? " "+p.getLastName() : "");
		this.type = p.getType()!=null ? p.getType().getName() : null;
		this.hiringDate = p.getHiringDate()!=null ? format(p.getHiringDate()) : null;
		this.resignationDate = p.getResignationDate()!=null ? format(p.getResignationDate()) : null;
	}
	
	private String format(Date dt) {
		return TDateUtil.getFormatedDate(dt, TDateUtil.DDMMYYYY);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHiringDate() {
		return hiringDate;
	}

	public void setHiringDate(String hiringDate) {
		this.hiringDate = hiringDate;
	}

	public String getResignationDate() {
		return resignationDate;
	}

	public void setResignationDate(String resignationDate) {
		this.resignationDate = resignationDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

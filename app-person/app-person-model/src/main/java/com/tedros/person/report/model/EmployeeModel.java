/**
 * 
 */
package com.tedros.person.report.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

import com.tedros.extension.model.Contact;
import com.tedros.person.model.Employee;

/**
 * @author Davis Gordon
 *
 */
public class EmployeeModel implements Serializable{
	
	private static final long serialVersionUID = 8263364630321441440L;

	private String name;
	
	private String hiringDate;
	
	private String resignationDate;
	
	private String type;
	
	private String contacts;

	public EmployeeModel(Employee p) {
		this.name = p.getName() + (p.getLastName()!=null ? " "+p.getLastName() : "");
		this.type = p.getType()!=null ? p.getType().getName() : null;
		this.hiringDate = p.getHiringDate()!=null ? format(p.getHiringDate()) : null;
		this.resignationDate = p.getResignationDate()!=null ? format(p.getResignationDate()) : null;
		this.contacts = Contact.toStringList(p.getContacts());
	}
	
	private String format(Date dt) {
		return DateFormat.getDateInstance(DateFormat.MEDIUM).format(dt);
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

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

}

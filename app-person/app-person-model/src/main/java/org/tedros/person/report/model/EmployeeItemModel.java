/**
 * 
 */
package org.tedros.person.report.model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.tedros.docs.report.model.DocumentItemModel;
import org.tedros.extension.model.Contact;
import org.tedros.person.model.Employee;
import org.tedros.server.model.ITReportItemModel;

/**
 * @author Davis Gordon
 *
 */
public class EmployeeItemModel implements ITReportItemModel<Employee> {

	private static final long serialVersionUID = 2413643979622464683L;

	private String name;
	
	private String hiringDate;
	
	private String resignationDate;
	
	private String employer;
	
	private String costCenter;
	
	private String birthDate;
	
	private String sex;
	
	private String gender;
	
	private String type;
	
	private String address;
	
	public String contacts;
	
	public String description;
	
	private String observation;
	
	private List<Attribute> attributes;
	
	public List<DocumentItemModel> documents;
	
	private Employee item;
	
	/**
	 * 
	 */
	public EmployeeItemModel() {
	}

	public EmployeeItemModel(Employee p) {
		this.item = p;
		this.name = p.getName() + (p.getLastName()!=null ? " "+p.getLastName() : "");
		this.type = p.getType()!=null ? p.getType().getName() : null;
		this.address = p.getAddress()!=null ? p.getAddress().toString() : null;
		this.contacts = Contact.toStringList(p.getContacts());
		this.description = p.getDescription();
		this.employer = p.getEmployer()!=null ? p.getEmployer().getName() : null;
		this.costCenter = p.getCostCenter()!=null ? p.getCostCenter().getName() : null;
		this.birthDate = p.getBirthDate()!=null ? format(p.getBirthDate()) : null;
		this.hiringDate = p.getHiringDate()!=null ? format(p.getHiringDate()) : null;
		this.resignationDate = p.getResignationDate()!=null ? format(p.getResignationDate()) : null;
		this.gender = p.getGender()!=null ? p.getGender().getValue() : null;
		this.sex = p.getSex()!=null ? p.getSex().getValue() : null;
		this.observation = p.getObservation();
		if(p.getAttributes()!=null) {
			this.attributes =new ArrayList<>();
			p.getAttributes().stream().sorted((a,b)->{
				return a.getName().compareToIgnoreCase(b.getName());
			}).forEach(c->{
				this.attributes.add(new Attribute(c));
			});
		}else
			this.attributes = null;
		
		if(p.getDocuments()!=null) {
			this.documents =new ArrayList<>();
			p.getDocuments().stream().sorted((a,b)->{
				return a.getName().compareToIgnoreCase(b.getName());
			}).forEach(c->{
				this.documents.add(new DocumentItemModel(c));
			});
		}else
			this.documents = null;
		
	}



	private String format(Date dt) {
		return DateFormat.getDateInstance(DateFormat.MEDIUM).format(dt);
	}

	@Override
	public Employee getModel() {
		return item;
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

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public List<DocumentItemModel> getDocuments() {
		return documents;
	}

	public void setDocuments(List<DocumentItemModel> documents) {
		this.documents = documents;
	}

	public Employee getItem() {
		return item;
	}

	/**
	 * @return the costCenter
	 */
	public String getCostCenter() {
		return costCenter;
	}

	/**
	 * @param costCenter the costCenter to set
	 */
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

}

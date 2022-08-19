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
import org.tedros.person.model.LegalPerson;

import org.tedros.server.model.ITReportItemModel;

/**
 * @author Davis Gordon
 *
 */
public class LegalPersonItemModel implements ITReportItemModel<LegalPerson> {

	private static final long serialVersionUID = 2413643979622464683L;

	private String name;
	
	private String otherName;
	
	private String type;
	
	private String startActivities;

	private String endActivities;
	
	public List<EmployeeModel> staff;
	
	private String address;
	
	public String contacts;
	
	public String description;
	
	private String observation;
	
	private List<Attribute> attributes;
	
	public List<DocumentItemModel> documents;
	
	private LegalPerson item;
	
	/**
	 * 
	 */
	public LegalPersonItemModel() {
	}

	public LegalPersonItemModel(LegalPerson p) {
		this.item = p;
		this.name = p.getName();
		this.otherName = p.getOtherName();
		this.type = p.getType()!=null ? p.getType().getName() : null;
		this.address = p.getAddress()!=null ? p.getAddress().toString() : null;
		this.contacts = Contact.toStringList(p.getContacts());
		this.description = p.getDescription();
		this.startActivities = p.getStartActivities()!=null ? format(p.getStartActivities()) : null;
		this.endActivities = p.getEndActivities()!=null ? format(p.getEndActivities()) : null;
		this.observation = p.getObservation();
		if(p.getStaff()!=null) {
			this.staff =new ArrayList<>();
			p.getStaff().stream().sorted((a,b)->{
				return a.getName().compareToIgnoreCase(b.getName());
			}).forEach(c->{
				this.staff.add(new EmployeeModel(c));
			});
		}else
			this.staff = null;
		
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
	public LegalPerson getModel() {
		return item;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public LegalPerson getItem() {
		return item;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public String getStartActivities() {
		return startActivities;
	}

	public void setStartActivities(String startActivities) {
		this.startActivities = startActivities;
	}

	public String getEndActivities() {
		return endActivities;
	}

	public void setEndActivities(String endActivities) {
		this.endActivities = endActivities;
	}

	public List<EmployeeModel> getStaff() {
		return staff;
	}

	public void setStaff(List<EmployeeModel> staff) {
		this.staff = staff;
	}

}

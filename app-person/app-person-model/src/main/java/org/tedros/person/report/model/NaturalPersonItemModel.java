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
import org.tedros.person.model.NaturalPerson;
import org.tedros.server.model.ITReportItemModel;

/**
 * @author Davis Gordon
 *
 */
public class NaturalPersonItemModel implements ITReportItemModel<NaturalPerson> {

	private static final long serialVersionUID = 2413643979622464683L;

	private String name;
	
	private String birthDate;
	
	private String sex;
	
	private String gender;
	
	private String civilStatus;
	
	private String address;
	
	public String contacts;
	
	public String description;
	
	private String observation;
	
	private List<Attribute> attributes;
	
	public List<DocumentItemModel> documents;
	
	private List<Event> events;
	
	private NaturalPerson item;
	
	/**
	 * 
	 */
	public NaturalPersonItemModel() {
	}

	public NaturalPersonItemModel(NaturalPerson p) {
		this.item = p;
		this.name = p.getName() + (p.getLastName()!=null ? " "+p.getLastName() : "");
		this.address = p.getAddress()!=null ? p.getAddress().toString() : null;
		this.contacts = Contact.toStringList(p.getContacts());
		this.description = p.getDescription();
		this.birthDate = p.getBirthDate()!=null ? format(p.getBirthDate()) : null;
		this.gender = p.getGender()!=null ? p.getGender().getValue() : null;
		this.sex = p.getSex()!=null ? p.getSex().getValue() : null;
		this.civilStatus = p.getCivilStatus()!=null ? p.getCivilStatus().getValue() : null;
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

		if(p.getEvents()!=null) {
			this.events =new ArrayList<>();
			p.getEvents().stream().sorted((a,b)->{
				return a.getInsertDate().compareTo(b.getInsertDate());
			}).forEach(c->{
				this.events.add(new Event(c));
			});
		}else
			this.events = null;
		
	}



	private String format(Date dt) {
		return DateFormat.getDateInstance(DateFormat.MEDIUM).format(dt);
	}

	@Override
	public NaturalPerson getModel() {
		return item;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public NaturalPerson getItem() {
		return item;
	}

	public String getCivilStatus() {
		return civilStatus;
	}

	public void setCivilStatus(String civilStatus) {
		this.civilStatus = civilStatus;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

}

/**
 * 
 */
package org.tedros.extension.report.model;

import java.util.ArrayList;
import java.util.List;

import org.tedros.extension.model.Contact;
import org.tedros.extension.model.Document;
import org.tedros.server.model.ITReportItemModel;

/**
 * @author Davis Gordon
 *
 */
public class DocumentItemModel implements ITReportItemModel<Document> {

	private static final long serialVersionUID = -2907315933241507900L;

	private String code;
	
	private String name;
	
	private String value;
	
	private String type;
	
	private String state;
	
	private String observation;
	
	private String content;

	private String file;
	
	public String contacts;
	
	private List<Event> events;
	
	private Document item;

	public DocumentItemModel(Document e) {
		this.code = e.getCode();
		this.name = e.getName();
		this.value = e.getValue();
		this.type = e.getType()!=null ? e.getType().getName() : null;
		this.state = e.getStatus()!=null ? e.getStatus().getName() : null;
		this.observation = e.getObservation();
		this.content = e.getContent();
		this.file = e.getFile()!=null ? e.getFile().getFileName() : null;
		this.contacts = Contact.toStringList(e.contacts);
		if(e.getEvents()!=null) {
			this.events = new ArrayList<>();
			e.getEvents().stream().sorted((a,b)->{
				return a.getId().compareTo(b.getId());
			}).forEach(c->{
				this.events.add(new Event(c));
			});
		}else
			this.events = null;
		this.item = e;
	}

	@Override
	public Document getModel() {
		return item;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Document getItem() {
		return item;
	}

	public void setItem(Document item) {
		this.item = item;
	}
}

/**
 * 
 */
package org.tedros.extension.report.model;

import java.io.Serializable;
import java.text.DateFormat;

import org.tedros.extension.model.Contact;
import org.tedros.extension.model.DocumentEvent;

/**
 * @author Davis Gordon
 *
 */
public class Event implements Serializable {

	private static final long serialVersionUID = 3211767080513052227L;

	private String title;
	
	private String description;
	
	private String dateEvent;
	
	public String contacts;

	public Event(DocumentEvent e) {
		this.title = e.getTitle();
		this.description = e.getDescription();
		this.dateEvent = e.getDateEvent()!=null 
				? DateFormat
						.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM).format(e.getDateEvent()) 
						: null;
		this.contacts = Contact.toStringList(e.getContacts());
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDateEvent() {
		return dateEvent;
	}

	public void setDateEvent(String dateEvent) {
		this.dateEvent = dateEvent;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	
	
	
}

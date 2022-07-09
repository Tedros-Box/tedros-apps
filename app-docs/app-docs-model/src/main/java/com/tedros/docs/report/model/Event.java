/**
 * 
 */
package com.tedros.docs.report.model;

import java.io.Serializable;

import com.tedros.docs.model.DocumentEvent;
import com.tedros.extension.model.Contact;
import com.tedros.util.TDateUtil;

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
		this.dateEvent = e.getDateEvent()!=null ? TDateUtil.getFormatedDate(e.getDateEvent(), TDateUtil.DDMMYYYY_HHMM) : null;
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

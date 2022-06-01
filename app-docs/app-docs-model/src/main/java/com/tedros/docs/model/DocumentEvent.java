/**
 * 
 */
package com.tedros.docs.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.tedros.docs.domain.DomainSchema;
import com.tedros.docs.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;
import com.tedros.extension.model.Contact;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.document_event, schema = DomainSchema.schema)
public class DocumentEvent extends TEntity {
	
	private static final long serialVersionUID = -451351680126600688L;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="doc_id")
	private Document document;
	
	@Column(length=60, nullable = false)
	private String title;
	
	@Column(length=250)
	private String description;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateEvent;
	
	@Column(length=15)
	private String warn;
	
	@Column(length=15)
	private String statusWarning;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name=DomainTables.document_event_contact, 
	schema=DomainSchema.schema,
	joinColumns=@JoinColumn(name="event_id"), 
	inverseJoinColumns=@JoinColumn(name="contact_id"),
	uniqueConstraints=@UniqueConstraint(name="docEventContactUK", 
	columnNames = { "event_id","contact_id"}))
	public List<Contact> contacts;
	
	@Column
	private String content;
	
	
	public DocumentEvent() {
	}

	@Override
	public String toString() {
		return (title != null ? title : "");
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

	public Date getDateEvent() {
		return dateEvent;
	}

	public void setDateEvent(Date dateEvent) {
		this.dateEvent = dateEvent;
	}

	public String getStatusWarning() {
		return statusWarning;
	}

	public void setStatusWarning(String statusWarning) {
		this.statusWarning = statusWarning;
	}

	public String getWarn() {
		return warn;
	}

	public void setWarn(String warn) {
		this.warn = warn;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	

}

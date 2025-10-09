/**
 * 
 */
package org.tedros.extension.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;

import org.tedros.extension.domain.DomainSchema;
import org.tedros.extension.domain.DomainTables;
import org.tedros.server.entity.TVersionEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.document_event, schema = DomainSchema.schema)
public class DocumentEvent extends TVersionEntity {
	
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

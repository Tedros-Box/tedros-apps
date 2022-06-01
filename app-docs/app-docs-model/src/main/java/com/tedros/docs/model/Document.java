/**
 * 
 */
package com.tedros.docs.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.tedros.common.model.TFileEntity;
import com.tedros.docs.domain.DomainSchema;
import com.tedros.docs.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;
import com.tedros.extension.model.Contact;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.document, schema = DomainSchema.schema)
public class Document extends TEntity {
	
	private static final long serialVersionUID = -2382651197866734106L;

	@Column(length=10, nullable = false)
	private String code;
	
	@Column(length=60, nullable = false)
	private String title;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="doctype_id")
	private DocumentType type;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="docstate_id")
	private DocumentState state;

	@Column(length=60, nullable = true)
	private String summary;
	
	@Column(length=120, nullable = true)
	private String observation;
	
	@Column(length=15, nullable = true)
	private String content;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="file_id")
	private TFileEntity file;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name=DomainTables.document_contact, 
	schema=DomainSchema.schema,
	joinColumns=@JoinColumn(name="doc_id"), 
	inverseJoinColumns=@JoinColumn(name="contact_id"),
	uniqueConstraints=@UniqueConstraint(name="docContactUK", 
	columnNames = { "doc_id","contact_id"}))
	public Set<Contact> contacts;
	
	@OneToMany(mappedBy="document", fetch=FetchType.EAGER)
	private Set<DocumentEvent> events;
	
	public Document() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public DocumentType getType() {
		return type;
	}

	public void setType(DocumentType type) {
		this.type = type;
	}

	public DocumentState getState() {
		return state;
	}

	public void setState(DocumentState state) {
		this.state = state;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
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

	public TFileEntity getFile() {
		return file;
	}

	public void setFile(TFileEntity file) {
		this.file = file;
	}

	public Set<DocumentEvent> getEvents() {
		return events;
	}

	public void setEvents(Set<DocumentEvent> events) {
		this.events = events;
	}

	@Override
	public String toString() {
		return (title != null ? title : "");
	}

	public Set<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}

}

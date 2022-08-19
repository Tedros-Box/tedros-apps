/**
 * 
 */
package org.tedros.docs.model;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.tedros.docs.domain.DomainSchema;
import org.tedros.docs.domain.DomainTables;
import org.tedros.extension.model.Contact;

import org.tedros.common.model.TFileEntity;
import org.tedros.server.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.document, schema = DomainSchema.schema)
public class Document extends TEntity {
	
	private static final long serialVersionUID = -2382651197866734106L;

	@Column(length=10)
	private String code;
	
	@Column(length=120, nullable = false)
	private String name;
	
	@Column()
	private String value;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="doctype_id")
	private DocumentType type;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="docstate_id")
	private DocumentState state;
	
	@Column()
	private String observation;
	
	@Column()
	private String content;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
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
	
/*
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name=DomainTables.document_notify, 
	schema=DomainSchema.schema,
	joinColumns=@JoinColumn(name="doc_id"), 
	inverseJoinColumns=@JoinColumn(name="notify_id"),
	uniqueConstraints=@UniqueConstraint(name="docNotifyUK", 
	columnNames = { "doc_id","notify_id"}))
	public Set<TNotify> notification;*/
	
	public Document() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
		return (name != null ? name : "");
	}

	public Set<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
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

}

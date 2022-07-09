/**
 * 
 */
package com.tedros.person.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.tedros.docs.model.Document;
import com.tedros.ejb.base.entity.TReceptiveEntity;
import com.tedros.extension.model.Contact;
import com.tedros.location.model.Address;
import com.tedros.person.domain.DomainSchema;
import com.tedros.person.domain.DomainTables;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.person, schema = DomainSchema.schema)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DTYPE", discriminatorType=DiscriminatorType.STRING, length=1)
@DiscriminatorValue("P")
public class Person extends TReceptiveEntity {

	private static final long serialVersionUID = -1790917959813402388L;

	@Column(length=120, nullable = false)
	private String name;
	
	@Column(length=2000)
	private String description;
	
	@Column(length=2000)
	private String observation;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="address_id")
	private Address address;
	
	@OneToMany(orphanRemoval=true, 
			cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="id_person", nullable=false, updatable=false)
	private Set<PersonAttributes> attributes;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name=DomainTables.person_contact, 
	schema=DomainSchema.schema,
	joinColumns=@JoinColumn(name="person_id"), 
	inverseJoinColumns=@JoinColumn(name="contact_id"),
	uniqueConstraints=@UniqueConstraint(name="PersonContactUK", 
	columnNames = { "person_id","contact_id"}))
	public Set<Contact> contacts;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name=DomainTables.person_document, 
	schema=DomainSchema.schema,
	joinColumns=@JoinColumn(name="person_id"), 
	inverseJoinColumns=@JoinColumn(name="doc_id"),
	uniqueConstraints=@UniqueConstraint(name="PersonDocumentUK", 
	columnNames = { "person_id","doc_id"}))
	public Set<Document> documents;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<PersonAttributes> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<PersonAttributes> attributes) {
		this.attributes = attributes;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}

	public Set<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}
	
}

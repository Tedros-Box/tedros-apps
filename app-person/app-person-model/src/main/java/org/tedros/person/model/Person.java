/**
 * 
 */
package org.tedros.person.model;

import java.util.Set;

import javax.persistence.Cacheable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.tedros.common.model.TFileEntity;
import org.tedros.extension.model.Address;
import org.tedros.extension.model.Contact;
import org.tedros.extension.model.Document;
import org.tedros.person.domain.DomainSchema;
import org.tedros.person.domain.DomainTables;
import org.tedros.server.entity.ITDiscriminable;
import org.tedros.server.entity.TReceptiveEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Cacheable(false)
@Table(name = DomainTables.person, schema = DomainSchema.schema)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DTYPE", discriminatorType=DiscriminatorType.STRING, length=10)
@DiscriminatorValue("P")
public class Person extends TReceptiveEntity implements ITDiscriminable{

	private static final long serialVersionUID = -1790917959813402388L;

	@JsonIgnore
	@Column(length=10)
	private String dType;
	
	@Column(length=120, nullable = false)
	private String name;
	
	@Column(length=2000)
	private String description;
	
	@Column(length=2000)
	private String observation;
	
	@JsonPropertyDescription("The person type")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="type_id")
	private PersonType type;

	@JsonPropertyDescription("The person status")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="status_id")
	private PersonStatus status;
	
	@JsonIgnore
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="image_id")
	private TFileEntity image;
	
	@JsonPropertyDescription("The person address")
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="address_id")
	private Address address;

	@JsonPropertyDescription("Added Events")
	@OneToMany(orphanRemoval=true, 
			cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="id_person", nullable=false, updatable=false)
	private Set<PersonEvent> events;
	
	@JsonPropertyDescription("Additional attributes")
	@OneToMany(orphanRemoval=true, 
			cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="id_person", nullable=false, updatable=false)
	private Set<PersonAttributes> attributes;
	
	@JsonPropertyDescription("Contacts (e-mail, fax, phone, mobile number and others")
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name=DomainTables.person_contact, 
	schema=DomainSchema.schema,
	joinColumns=@JoinColumn(name="person_id"), 
	inverseJoinColumns=@JoinColumn(name="contact_id"),
	uniqueConstraints=@UniqueConstraint(name="PersonContactUK", 
	columnNames = { "person_id","contact_id"}))
	private Set<Contact> contacts;
	
	@JsonPropertyDescription("Documents (ID number, Fiscal number, contracts and others)")
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name=DomainTables.person_document, 
	schema=DomainSchema.schema,
	joinColumns=@JoinColumn(name="person_id"), 
	inverseJoinColumns=@JoinColumn(name="doc_id"),
	uniqueConstraints=@UniqueConstraint(name="PersonDocumentUK", 
	columnNames = { "person_id","doc_id"}))
	private Set<Document> documents;
	
	@JsonPropertyDescription("Categories")
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name=DomainTables.personcateg_person, 
	schema=DomainSchema.schema,
	inverseJoinColumns=@JoinColumn(name="categ_id"), 
	joinColumns=@JoinColumn(name="person_id"),
	uniqueConstraints=@UniqueConstraint(name="PersonCategoriestUK", 
	columnNames = { "person_id","categ_id"}))
	private Set<PersonCategory> categories;
	
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

	public String getdType() {
		return dType;
	}

	public void setdType(String dType) {
		this.dType = dType;
	}

	@Override
	public String toString() {
		return (name != null ?  name : "");
	}

	public Set<PersonEvent> getEvents() {
		return events;
	}

	public void setEvents(Set<PersonEvent> events) {
		this.events = events;
	}

	/**
	 * @return the image
	 */
	public TFileEntity getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(TFileEntity image) {
		this.image = image;
	}

	@Override
	public String getDiscriminatorDesc() {
		return "";
	}

	public PersonType getType() {
		return type;
	}

	public void setType(PersonType type) {
		this.type = type;
	}

	public PersonStatus getStatus() {
		return status;
	}

	public void setStatus(PersonStatus status) {
		this.status = status;
	}

	/**
	 * @return the categories
	 */
	public Set<PersonCategory> getCategories() {
		return categories;
	}

	/**
	 * @param categories the categories to set
	 */
	public void setCategories(Set<PersonCategory> categories) {
		this.categories = categories;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((categories == null) ? 0 : categories.hashCode());
		result = prime * result + ((contacts == null) ? 0 : contacts.hashCode());
		result = prime * result + ((dType == null) ? 0 : dType.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((documents == null) ? 0 : documents.hashCode());
		result = prime * result + ((events == null) ? 0 : events.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((observation == null) ? 0 : observation.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (categories == null) {
			if (other.categories != null)
				return false;
		} else if (!categories.equals(other.categories))
			return false;
		if (contacts == null) {
			if (other.contacts != null)
				return false;
		} else if (!contacts.equals(other.contacts))
			return false;
		if (dType == null) {
			if (other.dType != null)
				return false;
		} else if (!dType.equals(other.dType))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (documents == null) {
			if (other.documents != null)
				return false;
		} else if (!documents.equals(other.documents))
			return false;
		if (events == null) {
			if (other.events != null)
				return false;
		} else if (!events.equals(other.events))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (observation == null) {
			if (other.observation != null)
				return false;
		} else if (!observation.equals(other.observation))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}

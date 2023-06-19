/**
 * 
 */
package org.tedros.sample.entity;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.tedros.common.model.TFileEntity;
import org.tedros.extension.model.Contact;
import org.tedros.extension.model.Document;
import org.tedros.person.model.NaturalPerson;
import org.tedros.sample.domain.DomainSchema;
import org.tedros.sample.domain.DomainTables;
import org.tedros.server.entity.TVersionEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Cacheable(false)
@Table(name = DomainTables.samplea, schema = DomainSchema.schema)
public class SampleA extends TVersionEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 299519363333302670L;

	@Column
	private String name;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="image_id")
	private TFileEntity image;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="person_id")
	private NaturalPerson person;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="samplec_id")
	private SampleC detail;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name=DomainTables.samplea_sampleb, 
	schema=DomainSchema.schema,
	joinColumns=@JoinColumn(name="samplea_id"), 
	inverseJoinColumns=@JoinColumn(name="sampleb_id"),
	uniqueConstraints=@UniqueConstraint(name="samplesUK", 
	columnNames = { "samplea_id","sampleb_id"}))
	private Set<SampleB> details;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name=DomainTables.sample_cont, 
	schema=DomainSchema.schema,
	joinColumns=@JoinColumn(name="sample_id"), 
	inverseJoinColumns=@JoinColumn(name="contact_id"),
	uniqueConstraints=@UniqueConstraint(name="sampleContactUK", 
	columnNames = { "sample_id","contact_id"}))
	private Set<Contact> contacts;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name=DomainTables.sample_doc, 
	schema=DomainSchema.schema,
	joinColumns=@JoinColumn(name="sample_id"), 
	inverseJoinColumns=@JoinColumn(name="doc_id"),
	uniqueConstraints=@UniqueConstraint(name="sampleDocumentUK", 
	columnNames = { "sample_id","doc_id"}))
	private Set<Document> documents;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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

	/**
	 * @return the person
	 */
	public NaturalPerson getPerson() {
		return person;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(NaturalPerson person) {
		this.person = person;
	}

	/**
	 * @return the detail
	 */
	public SampleC getDetail() {
		return detail;
	}

	/**
	 * @param detail the detail to set
	 */
	public void setDetail(SampleC detail) {
		this.detail = detail;
	}

	/**
	 * @return the details
	 */
	public Set<SampleB> getDetails() {
		return details;
	}

	/**
	 * @param details the details to set
	 */
	public void setDetails(Set<SampleB> details) {
		this.details = details;
	}

	/**
	 * @return the contacts
	 */
	public Set<Contact> getContacts() {
		return contacts;
	}

	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}

	/**
	 * @return the documents
	 */
	public Set<Document> getDocuments() {
		return documents;
	}

	/**
	 * @param documents the documents to set
	 */
	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((contacts == null) ? 0 : contacts.hashCode());
		result = prime * result + ((detail == null) ? 0 : detail.hashCode());
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + ((documents == null) ? 0 : documents.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((person == null) ? 0 : person.hashCode());
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
		SampleA other = (SampleA) obj;
		if (contacts == null) {
			if (other.contacts != null)
				return false;
		} else if (!contacts.equals(other.contacts))
			return false;
		if (detail == null) {
			if (other.detail != null)
				return false;
		} else if (!detail.equals(other.detail))
			return false;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		if (documents == null) {
			if (other.documents != null)
				return false;
		} else if (!documents.equals(other.documents))
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
		if (person == null) {
			if (other.person != null)
				return false;
		} else if (!person.equals(other.person))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return  (name != null ? name : "") ;
	}
	
}

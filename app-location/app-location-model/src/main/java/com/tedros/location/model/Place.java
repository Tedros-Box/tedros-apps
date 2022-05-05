/**
 * 
 */
package com.tedros.location.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.tedros.common.model.TFileEntity;
import com.tedros.ejb.base.entity.TEntity;
import com.tedros.extension.model.Contact;
import com.tedros.location.domain.DomainSchema;
import com.tedros.location.domain.DomainTables;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.place, schema = DomainSchema.schema)
public class Place extends TEntity {

	private static final long serialVersionUID = 1L;

	@Column(length=60, nullable=false)
	private String title;
	
	@Column(length=500, nullable=true)
	private String description;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="address_id", nullable=false)
	private Address address;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name=DomainTables.place_contact, 
	schema=DomainSchema.schema,
	joinColumns=@JoinColumn(name="place_id"), 
	inverseJoinColumns=@JoinColumn(name="contact_id"),
	uniqueConstraints=@UniqueConstraint(name="placeContactUK", 
	columnNames = { "place_id","contact_id"}))
	public Set<Contact> contacts;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name=DomainTables.place_picture, 
	schema=DomainSchema.schema,
	joinColumns=@JoinColumn(name="place_id"), 
	inverseJoinColumns=@JoinColumn(name="file_id"),
	uniqueConstraints=@UniqueConstraint(name="placePictureUK", 
	columnNames = { "place_id","file_id"}))
	public Set<TFileEntity> pictures = new HashSet<>();

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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<TFileEntity> getPictures() {
		return pictures;
	}

	public void setPictures(Set<TFileEntity> pictures) {
		this.pictures = pictures;
	}

	public Set<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}

	@Override
	public String toString() {
		return title!=null ? title : "";
	}
}

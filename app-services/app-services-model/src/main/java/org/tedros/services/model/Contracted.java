/**
 * 
 */
package org.tedros.services.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.tedros.docs.model.Document;
import org.tedros.person.model.Person;
import org.tedros.server.entity.TVersionEntity;
import org.tedros.services.domain.DomainSchema;
import org.tedros.services.domain.DomainTables;
import org.tedros.services.domain.Status;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name=DomainTables.contracted, schema=DomainSchema.schema)
public class Contracted extends TVersionEntity {

	private static final long serialVersionUID = 6542389635444105197L;

	@ManyToOne
	@JoinColumn(name="contrac_id", nullable=false, updatable=false)
	private Contractor contractor;
	
	@ManyToOne
	@JoinColumn(name="person_id", nullable=false, updatable=false)
	private Person person;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name=DomainTables.contracted_serv_types, 
	schema=DomainSchema.schema,
	joinColumns=@JoinColumn(name="contr_id"), 
	inverseJoinColumns=@JoinColumn(name="type_id"),
	uniqueConstraints=@UniqueConstraint(name="ContractedServTypeUK", 
	columnNames = { "contr_id","type_id"}))
	private Set<ServiceType> serviceTypes;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name=DomainTables.contracted_docs, 
	schema=DomainSchema.schema,
	joinColumns=@JoinColumn(name="contr_id"), 
	inverseJoinColumns=@JoinColumn(name="doc_id"),
	uniqueConstraints=@UniqueConstraint(name="ContractedDocumentUK", 
	columnNames = { "contr_id","doc_id"}))
	private Set<Document> documents;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date beginDate;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	public Contractor getContractor() {
		return contractor;
	}

	public void setContractor(Contractor contractor) {
		this.contractor = contractor;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Set<ServiceType> getServiceTypes() {
		return serviceTypes;
	}

	public void setServiceTypes(Set<ServiceType> serviceTypes) {
		this.serviceTypes = serviceTypes;
	}

	public Set<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.tedros.docs.model.Document;
import org.tedros.person.model.Person;
import org.tedros.services.domain.DomainSchema;
import org.tedros.services.domain.DomainTables;
import org.tedros.services.domain.Status;

import org.tedros.server.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name=DomainTables.contract, schema=DomainSchema.schema)
public class Contract extends TEntity {

	private static final long serialVersionUID = 1295508432603341688L;

	@Column(length=15)
	private String code;

	@Column(length=250, nullable=false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name="contractor_id", nullable=false, updatable=false)
	private Person contractor;
	
	@ManyToOne
	@JoinColumn(name="contracted_id", nullable=false, updatable=false)
	private Person contracted;
	
	@Column(length=2000)
	private String description;
	
	@Column(length=2000)
	private String observation;
	
	@Column
	private String content;
	
	@OneToMany(orphanRemoval=true, 
			cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="id_contract", nullable=false, updatable=false)
	private Set<ContractualAgreement> agreements;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name=DomainTables.contract_docs, 
	schema=DomainSchema.schema,
	joinColumns=@JoinColumn(name="contr_id"), 
	inverseJoinColumns=@JoinColumn(name="doc_id"),
	uniqueConstraints=@UniqueConstraint(name="ContractDocumentUK", 
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Person getContractor() {
		return contractor;
	}

	public void setContractor(Person contractor) {
		this.contractor = contractor;
	}

	public Person getContracted() {
		return contracted;
	}

	public void setContracted(Person contracted) {
		this.contracted = contracted;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<ContractualAgreement> getAgreements() {
		return agreements;
	}

	public void setAgreements(Set<ContractualAgreement> agreements) {
		this.agreements = agreements;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}

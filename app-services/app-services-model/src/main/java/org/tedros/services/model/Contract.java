/**
 * 
 */
package org.tedros.services.model;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;

import org.tedros.extension.model.Document;
import org.tedros.person.model.Person;
import org.tedros.server.entity.TVersionEntity;
import org.tedros.services.domain.DomainSchema;
import org.tedros.services.domain.DomainTables;
import org.tedros.services.domain.Status;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name=DomainTables.contract, schema=DomainSchema.schema)
@JsonClassDescription("service contract")
public class Contract extends TVersionEntity {

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
	@JsonPropertyDescription("contractual agreements on payment amounts")
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
	@JsonPropertyDescription("contract status can be ENABLED, DISABLED, or SUSPENDED")
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

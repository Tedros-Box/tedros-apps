/**
 * 
 */
package org.tedros.services.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.tedros.server.entity.TVersionEntity;
import org.tedros.services.domain.DomainSchema;
import org.tedros.services.domain.DomainTables;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name=DomainTables.service, schema=DomainSchema.schema)
@JsonClassDescription("service to be provided")
public class Service extends TVersionEntity {
	
	private static final long serialVersionUID = -264423263431230419L;

	@Column(length=15)
	private String code;

	@Column(length=250, nullable = false)
	private String name;
	
	@Column(length=1024)
	private String description;
	
	@Column(length=1024)
	private String observation;
	
	@ManyToOne
	@JoinColumn(name="serv_type_id", nullable=false)
	private ServiceType type;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name=DomainTables.service_plan, 
	schema=DomainSchema.schema,
	joinColumns=@JoinColumn(name="serv_id"),
	inverseJoinColumns=@JoinColumn(name="plan_id"),
	uniqueConstraints=@UniqueConstraint(name="ServPlanUK", 
	columnNames = { "serv_id","plan_id"}))
	@JsonPropertyDescription("payment plans")
	private Set<Plan> plans;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

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

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public ServiceType getType() {
		return type;
	}

	public void setType(ServiceType type) {
		this.type = type;
	}

	public Set<Plan> getPlans() {
		return plans;
	}

	public void setPlans(Set<Plan> plans) {
		this.plans = plans;
	}

	@Override
	public String toString() {
		return  (name != null ?  name : "");
	}
	
	
}

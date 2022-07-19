/**
 * 
 */
package com.tedros.services.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tedros.ejb.base.entity.TEntity;
import com.tedros.services.domain.DomainSchema;
import com.tedros.services.domain.DomainTables;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name=DomainTables.contractual_agreement, schema=DomainSchema.schema)
public class ContractualAgreement extends TEntity {

	private static final long serialVersionUID = 4117114546282387246L;

	@Column(length=60, nullable=false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name="serv_type_id", nullable=false)
	private ServiceType serviceType;

	@Column
	private Integer amount;
	
	@Column
	private BigDecimal value;
	
	@Column()
	private Double percentage;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return   (name != null ? name + ", " : "");
	}

}

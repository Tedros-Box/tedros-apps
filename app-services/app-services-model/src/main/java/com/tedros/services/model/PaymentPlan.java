/**
 * 
 */
package com.tedros.services.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tedros.ejb.base.entity.TEntity;
import com.tedros.services.domain.DomainSchema;
import com.tedros.services.domain.DomainTables;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name=DomainTables.payment_plan, schema=DomainSchema.schema)
public class PaymentPlan extends TEntity {

	private static final long serialVersionUID = 5871249619622576729L;

	@Column(nullable=false)
	private Integer amount;
	
	@Column()
	private Double discount;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
}

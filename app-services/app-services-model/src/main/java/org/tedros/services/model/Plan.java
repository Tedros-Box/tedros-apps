/**
 * 
 */
package org.tedros.services.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.tedros.server.entity.TVersionEntity;
import org.tedros.services.domain.DomainSchema;
import org.tedros.services.domain.DomainTables;
import org.tedros.services.domain.Status;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name=DomainTables.plan, schema=DomainSchema.schema)
public class Plan extends TVersionEntity {

	private static final long serialVersionUID = 8033991483988619245L;

	@Column(length=250, nullable = false)
	private String name;
	
	@Column(length=1024)
	private String description;
	
	@Column
	private BigDecimal registrationFee;
	
	@Column
	private BigDecimal value;
	
	@OneToMany(orphanRemoval=true, 
			cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="id_plan", nullable=false, updatable=false)
	private List<PaymentPlan> payments;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date beginDate;
	

	@Column
	@Temporal(TemporalType.DATE)
	private Date endDate;

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

	public BigDecimal getRegistrationFee() {
		return registrationFee;
	}

	public void setRegistrationFee(BigDecimal registrationFee) {
		this.registrationFee = registrationFee;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public List<PaymentPlan> getPayments() {
		return payments;
	}

	public void setPayments(List<PaymentPlan> payments) {
		this.payments = payments;
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
	

	@Override
	public String toString() {
		return  (name != null ?  name : "");
	}
	
	
}

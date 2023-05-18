/**
 * 
 */
package org.tedros.sample.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.tedros.person.model.CostCenter;
import org.tedros.person.model.ICostCenterAccounting;
import org.tedros.person.model.LegalPerson;
import org.tedros.sample.domain.DomainSchema;
import org.tedros.sample.domain.DomainTables;
import org.tedros.server.entity.TVersionEntity;
import org.tedros.stock.entity.Product;

/**
 * 
 * 
 * @author Davis
 *
 */
@Entity
@Table(name=DomainTables.product_price, schema=DomainSchema.schema,
uniqueConstraints= {@UniqueConstraint(columnNames = { "legal_person_id","cost_center_id","prod_id" })})
public class ProductPrice extends TVersionEntity implements ICostCenterAccounting {

	private static final long serialVersionUID = 6841828609438785671L;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="legal_person_id", nullable=false)
	private LegalPerson legalPerson;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cost_center_id", nullable=false)
	private CostCenter costCenter;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="prod_id", 
	nullable=false, updatable=false)
	private Product product;
	
	@Column(nullable = false)
	private BigDecimal unitPrice;
	

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * @return the unitPrice
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the legalPerson
	 */
	public LegalPerson getLegalPerson() {
		return legalPerson;
	}

	/**
	 * @param legalPerson the legalPerson to set
	 */
	public void setLegalPerson(LegalPerson legalPerson) {
		this.legalPerson = legalPerson;
	}

	/**
	 * @return the costCenter
	 */
	public CostCenter getCostCenter() {
		return costCenter;
	}

	/**
	 * @param costCenter the costCenter to set
	 */
	public void setCostCenter(CostCenter costCenter) {
		this.costCenter = costCenter;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((costCenter == null) ? 0 : costCenter.hashCode());
		result = prime * result + ((legalPerson == null) ? 0 : legalPerson.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((unitPrice == null) ? 0 : unitPrice.hashCode());
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
		ProductPrice other = (ProductPrice) obj;
		if (costCenter == null) {
			if (other.costCenter != null)
				return false;
		} else if (!costCenter.equals(other.costCenter))
			return false;
		if (legalPerson == null) {
			if (other.legalPerson != null)
				return false;
		} else if (!legalPerson.equals(other.legalPerson))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (unitPrice == null) {
			if (other.unitPrice != null)
				return false;
		} else if (!unitPrice.equals(other.unitPrice))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return  (product != null ? product  : "")
				+ (unitPrice != null ? ", " + unitPrice : "");
	}

}

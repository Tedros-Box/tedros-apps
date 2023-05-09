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

import org.tedros.sample.domain.DomainSchema;
import org.tedros.sample.domain.DomainTables;
import org.tedros.server.entity.TVersionEntity;
import org.tedros.stock.entity.Product;

/**
 * @author Davis
 *
 */
@Entity
@Table(name=DomainTables.sale_item, schema=DomainSchema.schema)
public class SaleItem extends TVersionEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 686915830719606030L;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="prod_id", 
	nullable=false, updatable=false)
	private Product product;
	
	@Column(nullable = false)
	private Integer amount;

	@Column(nullable = false)
	private BigDecimal unitPrice;
	
	@Column
	private Double rebate;

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
	 * @return the amount
	 */
	public Integer getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
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
	 * @return the rebate
	 */
	public Double getRebate() {
		return rebate;
	}

	/**
	 * @param rebate the rebate to set
	 */
	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((rebate == null) ? 0 : rebate.hashCode());
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
		if (!(obj instanceof SaleItem))
			return false;
		SaleItem other = (SaleItem) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (rebate == null) {
			if (other.rebate != null)
				return false;
		} else if (!rebate.equals(other.rebate))
			return false;
		if (unitPrice == null) {
			if (other.unitPrice != null)
				return false;
		} else if (!unitPrice.equals(other.unitPrice))
			return false;
		return true;
	}

	
	
	
}

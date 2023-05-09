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
uniqueConstraints= {@UniqueConstraint(columnNames = { "prod_id" })})
public class ProductPrice extends TVersionEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6841828609438785671L;

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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		if (!(obj instanceof ProductPrice))
			return false;
		ProductPrice other = (ProductPrice) obj;
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

}

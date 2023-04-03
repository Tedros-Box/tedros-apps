/**
 * 
 */
package org.tedros.stock.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.tedros.server.entity.TEntity;
import org.tedros.stock.domain.DomainSchema;
import org.tedros.stock.domain.DomainTables;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.stock_config_item, schema = DomainSchema.schema)
public class StockConfigItem extends TEntity  {

	private static final long serialVersionUID = -3216481324029671441L;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="prod_id", nullable=false)
	private Product product;
	
	@Column
	private Boolean notify;

	@Column(nullable = false)
	private Double minimumAmount;
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Double getMinimumAmount() {
		return minimumAmount;
	}

	public void setMinimumAmount(Double minimumAmount) {
		this.minimumAmount = minimumAmount;
	}

	public Boolean getNotify() {
		return notify;
	}

	public void setNotify(Boolean notify) {
		this.notify = notify;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((minimumAmount == null) ? 0 : minimumAmount.hashCode());
		result = prime * result + ((notify == null) ? 0 : notify.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof StockConfigItem))
			return false;
		StockConfigItem other = (StockConfigItem) obj;
		if (minimumAmount == null) {
			if (other.minimumAmount != null)
				return false;
		} else if (!minimumAmount.equals(other.minimumAmount))
			return false;
		if (notify == null) {
			if (other.notify != null)
				return false;
		} else if (!notify.equals(other.notify))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

	
}

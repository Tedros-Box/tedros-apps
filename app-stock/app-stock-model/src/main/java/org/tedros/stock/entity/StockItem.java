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
@Table(name = DomainTables.stock_item, schema = DomainSchema.schema)
public class StockItem extends TEntity implements StockableItem {

	private static final long serialVersionUID = -3216481324029671441L;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="prod_id", nullable=false)
	private Product product;
	
	@Column(nullable = false)
	private Double amount;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="entry_id")
	private StockEntry entry;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="out_id")
	private StockOut out;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public StockEntry getEntry() {
		return entry;
	}

	public void setEntry(StockEntry entry) {
		this.entry = entry;
	}

	public StockOut getOut() {
		return out;
	}

	public void setOut(StockOut out) {
		this.out = out;
	}

	@Override
	public String toString() {
		return (product != null ?  product  : "")
				+ (amount != null ? " (" + amount +")" : "") + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((entry == null) ? 0 : entry.hashCode());
		result = prime * result + ((out == null) ? 0 : out.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof StockItem))
			return false;
		StockItem other = (StockItem) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (entry == null) {
			if (other.entry != null)
				return false;
		} else if (!entry.equals(other.entry))
			return false;
		if (out == null) {
			if (other.out != null)
				return false;
		} else if (!out.equals(other.out))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

}

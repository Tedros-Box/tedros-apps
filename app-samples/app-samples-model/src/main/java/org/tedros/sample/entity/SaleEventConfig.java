/**
 * 
 */
package org.tedros.sample.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.tedros.sample.domain.DomainSchema;
import org.tedros.sample.domain.DomainTables;
import org.tedros.server.entity.TVersionEntity;
import org.tedros.stock.entity.EntryType;
import org.tedros.stock.entity.OutType;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.sale_event_config, schema = DomainSchema.schema)
public class SaleEventConfig extends TVersionEntity {

	@Column
	private Boolean updateStock;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="out_type_id", nullable=true)
	private OutType outType;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="entry_type_id", nullable=true)
	private EntryType entryType;

	/**
	 * @return the updateStock
	 */
	public Boolean getUpdateStock() {
		return updateStock;
	}

	/**
	 * @param updateStock the updateStock to set
	 */
	public void setUpdateStock(Boolean updateStock) {
		this.updateStock = updateStock;
	}

	/**
	 * @return the outType
	 */
	public OutType getOutType() {
		return outType;
	}

	/**
	 * @param outType the outType to set
	 */
	public void setOutType(OutType outType) {
		this.outType = outType;
	}

	/**
	 * @return the entryType
	 */
	public EntryType getEntryType() {
		return entryType;
	}

	/**
	 * @param entryType the entryType to set
	 */
	public void setEntryType(EntryType entryType) {
		this.entryType = entryType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((entryType == null) ? 0 : entryType.hashCode());
		result = prime * result + ((outType == null) ? 0 : outType.hashCode());
		result = prime * result + ((updateStock == null) ? 0 : updateStock.hashCode());
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
		SaleEventConfig other = (SaleEventConfig) obj;
		if (entryType == null) {
			if (other.entryType != null)
				return false;
		} else if (!entryType.equals(other.entryType))
			return false;
		if (outType == null) {
			if (other.outType != null)
				return false;
		} else if (!outType.equals(other.outType))
			return false;
		if (updateStock == null) {
			if (other.updateStock != null)
				return false;
		} else if (!updateStock.equals(other.updateStock))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (updateStock != null ?  updateStock + ", " : "")
				+ (outType != null ?  outType + ", " : "")
				+ (entryType != null ?  entryType : "");
	}
}

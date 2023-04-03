/**
 * 
 */
package org.tedros.stock.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.tedros.person.model.Person;
import org.tedros.server.entity.TVersionEntity;
import org.tedros.stock.domain.DomainSchema;
import org.tedros.stock.domain.DomainTables;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.stock_config, schema = DomainSchema.schema, 
uniqueConstraints= {@UniqueConstraint(name="stockConfigUK",columnNames = {"cc_id"})})
public class StockConfig extends TVersionEntity {

	private static final long serialVersionUID = 4605036500373444451L;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cc_id", nullable=false)
	private CostCenter costCenter;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="person_id", nullable=true)
	private Person responsable;
	
	@OneToMany(fetch = FetchType.EAGER, 
			orphanRemoval=true, 
			cascade={CascadeType.ALL})
	@JoinColumn(name="conf_id")
	private List<StockConfigItem> items;

	public CostCenter getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(CostCenter costCenter) {
		this.costCenter = costCenter;
	}
	
	public List<StockConfigItem> getItems() {
		return items;
	}

	public void setItems(List<StockConfigItem> items) {
		this.items = items;
	}

	public Person getResponsable() {
		return responsable;
	}

	public void setResponsable(Person responsable) {
		this.responsable = responsable;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((costCenter == null) ? 0 : costCenter.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + ((responsable == null) ? 0 : responsable.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof StockConfig))
			return false;
		StockConfig other = (StockConfig) obj;
		if (costCenter == null) {
			if (other.costCenter != null)
				return false;
		} else if (!costCenter.equals(other.costCenter))
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		if (responsable == null) {
			if (other.responsable != null)
				return false;
		} else if (!responsable.equals(other.responsable))
			return false;
		return true;
	}


}

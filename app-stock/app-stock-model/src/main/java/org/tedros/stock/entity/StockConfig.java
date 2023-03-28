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

import org.tedros.server.entity.TVersionEntity;
import org.tedros.stock.domain.DomainSchema;
import org.tedros.stock.domain.DomainTables;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.stock_config, schema = DomainSchema.schema, 
uniqueConstraints= {@UniqueConstraint(name="stockConfigUK",columnNames = { "cc_id", "date" })})
public class StockConfig extends TVersionEntity {

	private static final long serialVersionUID = 4605036500373444451L;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cc_id", nullable=false)
	private CostCenter costCenter;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<StockConfigItem> getItems() {
		return items;
	}

	public void setItems(List<StockConfigItem> items) {
		this.items = items;
	}


}

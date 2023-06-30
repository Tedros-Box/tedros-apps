/**
 * 
 */
package org.tedros.stock.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.tedros.person.model.CostCenter;
import org.tedros.person.model.ICostCenterAccounting;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.model.Person;
import org.tedros.server.entity.TVersionEntity;
import org.tedros.stock.domain.DomainSchema;
import org.tedros.stock.domain.DomainTables;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.stock_event, schema = DomainSchema.schema)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DTYPE", length=10, 
discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("EVENT")
public class StockEvent extends TVersionEntity implements ICostCenterAccounting, Stockable {

	private static final long serialVersionUID = -996146247037445307L;

	@Column(length=10)
	private String dType;


	@JsonPropertyDescription("The legal person, owner")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="legal_person_id", nullable=false)
	private LegalPerson legalPerson;

	@JsonPropertyDescription("The cost center of product stock")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cost_center_id", nullable=false)
	private CostCenter costCenter;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@JsonPropertyDescription("The entry/output event type")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="type_id", nullable=true)
	private EventType type;
	

	@JsonPropertyDescription("The person responsible for this")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="person_id", nullable=true)
	private Person responsable;
	
	@Column()
	private String observation;
	
	@JsonPropertyDescription("List of items")
	@OneToMany(fetch = FetchType.EAGER, 
			mappedBy="event",
			orphanRemoval=true, 
			cascade={CascadeType.ALL})
	private List<StockItem> items;

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

	public Person getResponsable() {
		return responsable;
	}

	public void setResponsable(Person responsable) {
		this.responsable = responsable;
	}

	public List<StockItem> getItems() {
		return items;
	}

	public void setItems(List<StockItem> items) {
		this.items = items;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public String getdType() {
		return dType;
	}

	public void setdType(String dType) {
		this.dType = dType;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((costCenter == null) ? 0 : costCenter.hashCode());
		result = prime * result + ((dType == null) ? 0 : dType.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + ((legalPerson == null) ? 0 : legalPerson.hashCode());
		result = prime * result + ((observation == null) ? 0 : observation.hashCode());
		result = prime * result + ((responsable == null) ? 0 : responsable.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		StockEvent other = (StockEvent) obj;
		if (costCenter == null) {
			if (other.costCenter != null)
				return false;
		} else if (!costCenter.equals(other.costCenter))
			return false;
		if (dType == null) {
			if (other.dType != null)
				return false;
		} else if (!dType.equals(other.dType))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		if (legalPerson == null) {
			if (other.legalPerson != null)
				return false;
		} else if (!legalPerson.equals(other.legalPerson))
			return false;
		if (observation == null) {
			if (other.observation != null)
				return false;
		} else if (!observation.equals(other.observation))
			return false;
		if (responsable == null) {
			if (other.responsable != null)
				return false;
		} else if (!responsable.equals(other.responsable))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (type != null ? type + ", " : "")
				+ (date != null ? date + ", " : "")
				+ (legalPerson != null ? legalPerson + ", " : "")
				+ (costCenter != null ?  costCenter : "");
	}

}
/**
 * 
 */
package org.tedros.sample.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.tedros.extension.model.Address;
import org.tedros.person.model.CostCenter;
import org.tedros.person.model.Employee;
import org.tedros.person.model.ICostCenterAccounting;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.model.Person;
import org.tedros.sample.domain.DomainSchema;
import org.tedros.sample.domain.DomainTables;
import org.tedros.server.entity.TReceptiveEntity;

/**
 * @author Davis
 *
 */
@Entity
@Cacheable(false)
@Table(name=DomainTables.sale, schema=DomainSchema.schema)
public class Sale extends TReceptiveEntity implements ICostCenterAccounting{

	private static final long serialVersionUID = -8008690210025662586L;

	@Column(nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date date;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="legal_person_id", nullable=false)
	private LegalPerson legalPerson;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cost_center_id", nullable=false)
	private CostCenter costCenter;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="customer_id", nullable=false)
	private Person customer;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="employee_id")
	private Employee seller;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="address_id")
	private Address deliveryAddress;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="sale_type_id")
	private SaleType type;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="sale_status_id")
	private SaleStatus status;
	
	@OneToOne(fetch=FetchType.EAGER, 
			cascade= {CascadeType.MERGE})
	@JoinColumn(name="order_id", updatable=false)
	private Order order;
	
	@OneToMany(fetch=FetchType.EAGER, 
			cascade=CascadeType.ALL,
			orphanRemoval=true)
	@JoinColumn(name="sale_id")
	private List<SaleItem> items;

	public Sale() {
		
	}
	
	
	public Sale(Date date, Order o) {
		super();
		o.setSale(this);
		this.order = o;
		this.date = date;
		this.legalPerson = o.getLegalPerson();
		this.costCenter = o.getCostCenter();
		this.customer = o.getCustomer();
		this.seller = o.getSeller();
		this.deliveryAddress = o.getDeliveryAddress();
		this.items = new ArrayList<>();
		o.getItems().forEach(i->{
			this.items.add(new SaleItem(i));
		});
	}


	public CostCenter getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(CostCenter costCenter) {
		this.costCenter = costCenter;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the customer
	 */
	public Person getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Person customer) {
		this.customer = customer;
	}

	/**
	 * @return the seller
	 */
	public Employee getSeller() {
		return seller;
	}

	/**
	 * @param seller the seller to set
	 */
	public void setSeller(Employee seller) {
		this.seller = seller;
	}

	/**
	 * @return the deliveryAddress
	 */
	public Address getDeliveryAddress() {
		return deliveryAddress;
	}

	/**
	 * @param deliveryAddress the deliveryAddress to set
	 */
	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	/**
	 * @return the type
	 */
	public SaleType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(SaleType type) {
		this.type = type;
	}

	/**
	 * @return the status
	 */
	public SaleStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(SaleStatus status) {
		this.status = status;
	}

	/**
	 * @return the items
	 */
	public List<SaleItem> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<SaleItem> items) {
		this.items = items;
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
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}


	/**
	 * @param order the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((costCenter == null) ? 0 : costCenter.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((deliveryAddress == null) ? 0 : deliveryAddress.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + ((legalPerson == null) ? 0 : legalPerson.hashCode());
		result = prime * result + ((seller == null) ? 0 : seller.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Sale other = (Sale) obj;
		if (costCenter == null) {
			if (other.costCenter != null)
				return false;
		} else if (!costCenter.equals(other.costCenter))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (deliveryAddress == null) {
			if (other.deliveryAddress != null)
				return false;
		} else if (!deliveryAddress.equals(other.deliveryAddress))
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
		if (seller == null) {
			if (other.seller != null)
				return false;
		} else if (!seller.equals(other.seller))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
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
		return (legalPerson != null ?  legalPerson : "")
				+ (costCenter != null ? " [" + costCenter + "]" : "")
				+ (customer != null ? ", " + customer : "") 
				+ (date != null ? ", " + date : "");
	}

	
	
	
	

	
}

/**
 * 
 */
package org.tedros.stock.ejb.support;

import org.tedros.server.model.ITModel;
import org.tedros.stock.entity.Product;

/**
 * @author Davis Gordon
 *
 */
public class TItem implements ITModel {

	private static final long serialVersionUID = 1846735085323771965L;
	
	public enum TEvent { ENTRY, OUT };
	
	private Long id;
	
	private Product product;
	
	private Double amount;
	
	private String message;
	
	private TEvent event;

	public TItem(Product product, Double amount) {
		super();
		this.product = product;
		this.amount = amount;
		event = TEvent.OUT;
	}

	public TItem(Long id, Product product, Double amount) {
		super();
		this.id = id;
		this.product = product;
		this.amount = amount;
	}
	

	public TItem(Long id, Product product, Double amount, TEvent event) {
		super();
		this.id = id;
		this.product = product;
		this.amount = amount;
		this.event = event;
	}

	/**
	 * @return the event
	 */
	public TEvent getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(TEvent event) {
		this.event = event;
	}

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
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TItem other = (TItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TItem [" + (id != null ? "id=" + id + ", " : "") + (product != null ? "product=" + product + ", " : "")
				+ (amount != null ? "amount=" + amount + ", " : "") + (message != null ? "message=" + message : "")
				+ "]";
	}

}

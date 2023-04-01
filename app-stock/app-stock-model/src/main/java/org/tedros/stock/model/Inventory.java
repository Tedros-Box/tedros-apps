/**
 * 
 */
package org.tedros.stock.model;

import org.tedros.server.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public class Inventory implements ITModel {
	
	
	private static final long serialVersionUID = 3727717064033502648L;
	
	private Long prodId;
	private String code;
	private String name;
	private Double amount;

	/**
	 * 
	 */
	public Inventory() {
	}

	public Inventory(Long prodId, String code, String name, Double amount) {
		super();
		this.prodId = prodId;
		this.code = code;
		this.name = name;
		this.amount = amount;
	}

	public Long getProdId() {
		return prodId;
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}

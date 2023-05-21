/**
 * 
 */
package org.tedros.stock.model;

import org.tedros.server.model.ITReportItemModel;

/**
 * @author Davis Gordon
 *
 */
public class Inventory implements ITReportItemModel<Inventory>  {
	
	private static final long serialVersionUID = 3727717064033502648L;
	
	private Long prodId;
	private String legalPerson;
	private String costCenter;
	private String code;
	private String name;
	private Double amount;
	private Double minAmount = 0D;
	private Boolean allowNegativeStock = false;

	public Inventory() {
	}

	public Inventory(String legalPerson, String costCenter, Long prodId, String code, String name, Double amount) {
		super();
		this.legalPerson = legalPerson;
		this.costCenter = costCenter;
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

	@Override
	public Inventory getModel() {
		return this;
	}

	public Double getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(Double minAmount) {
		this.minAmount = minAmount;
	}

	/**
	 * @return the legalPerson
	 */
	public String getLegalPerson() {
		return legalPerson;
	}

	/**
	 * @param legalPerson the legalPerson to set
	 */
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	/**
	 * @return the costCenter
	 */
	public String getCostCenter() {
		return costCenter;
	}

	/**
	 * @param costCenter the costCenter to set
	 */
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	/**
	 * @return the allowNegativeStock
	 */
	public Boolean getAllowNegativeStock() {
		return allowNegativeStock;
	}

	/**
	 * @param allowNegativeStock the allowNegativeStock to set
	 */
	public void setAllowNegativeStock(Boolean allowNegativeStock) {
		this.allowNegativeStock = allowNegativeStock;
	}

}

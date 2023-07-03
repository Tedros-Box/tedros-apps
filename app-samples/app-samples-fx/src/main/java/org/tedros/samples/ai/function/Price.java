/**
 * 
 */
package org.tedros.samples.ai.function;

import java.math.BigDecimal;

import org.tedros.sample.entity.ProductPrice;

/**
 * @author Davis Gordon
 *
 */
public class Price {
	
	private String productCode;

	private String product;
	
	private String legalPerson;
	
	private String costCenter;
	
	private BigDecimal price;
	
	/**
	 * 
	 */
	public Price(ProductPrice pp) {
		this.productCode = pp.getProduct().getCode();
		this.product = pp.getProduct().getName();
		this.legalPerson = pp.getLegalPerson().toString();
		this.costCenter = pp.getCostCenter().toString();
		this.price = pp.getUnitPrice();
	}

	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(String product) {
		this.product = product;
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
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}

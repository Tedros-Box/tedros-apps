package org.tedros.stock.entity;

public interface StockableItem {

	/**
	 * @return the product
	 */
	Product getProduct();

	/**
	 * @param product the product to set
	 */
	void setProduct(Product product);

	/**
	 * @return the amount
	 */
	Double getAmount();

	/**
	 * @param amount the amount to set
	 */
	void setAmount(Double amount);


}
/**
 * 
 */
package org.tedros.stock.module.inventory.model;

import java.util.Date;
import java.util.List;

import org.tedros.server.model.ITModel;
import org.tedros.stock.entity.CostCenter;
import org.tedros.stock.entity.Product;
import org.tedros.stock.model.Inventory;

/**
 * @author Davis Gordon
 *
 */
public class CalcIventoryModel implements ITModel {

	private static final long serialVersionUID = 1608853644024500418L;

	private CostCenter costCenter;
	
	private Date date;
	
	private Product product;
	
	private List<Inventory> items;
	/**
	 * 
	 */
	public CalcIventoryModel() {
		// TODO Auto-generated constructor stub
	}
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
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public List<Inventory> getItems() {
		return items;
	}
	public void setItems(List<Inventory> items) {
		this.items = items;
	}

}

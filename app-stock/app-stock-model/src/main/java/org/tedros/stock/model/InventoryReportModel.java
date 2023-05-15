/**
 * 
 */
package org.tedros.stock.model;

import java.util.Date;

import org.tedros.person.model.CostCenter;
import org.tedros.server.model.TReportModel;
import org.tedros.stock.entity.Product;

/**
 * @author Davis Gordon
 *
 */
public class InventoryReportModel extends TReportModel<Inventory> {

	private static final long serialVersionUID = 1963594675124013687L;

	private CostCenter costCenter;
	
	private Date date;
	
	private Product product;

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
}

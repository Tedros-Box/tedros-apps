/**
 * 
 */
package org.tedros.stock.model;

import java.util.Date;

import org.tedros.person.model.CostCenter;
import org.tedros.person.model.ICostCenterAccounting;
import org.tedros.person.model.LegalPerson;
import org.tedros.server.model.TReportModel;
import org.tedros.stock.entity.Product;

/**
 * @author Davis Gordon
 *
 */
public class InventoryReportModel extends TReportModel<Inventory> implements ICostCenterAccounting {

	private static final long serialVersionUID = 1963594675124013687L;

	private LegalPerson legalPerson;
	
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
}

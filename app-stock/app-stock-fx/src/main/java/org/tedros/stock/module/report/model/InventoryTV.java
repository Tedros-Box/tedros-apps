/**
 * 
 */
package org.tedros.stock.module.report.model;

import org.tedros.fx.model.TModelView;
import org.tedros.stock.model.Inventory;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class InventoryTV extends TModelView<Inventory> {

	private SimpleStringProperty legalPerson;
	private SimpleStringProperty costCenter;
	private SimpleStringProperty code;
	private SimpleStringProperty name;
	private SimpleDoubleProperty amount;
	
	public InventoryTV(Inventory model) {
		super(model);
	}

	public SimpleStringProperty getCode() {
		return code;
	}

	public void setCode(SimpleStringProperty code) {
		this.code = code;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleDoubleProperty getAmount() {
		return amount;
	}

	public void setAmount(SimpleDoubleProperty amount) {
		this.amount = amount;
	}

	/**
	 * @return the legalPerson
	 */
	public SimpleStringProperty getLegalPerson() {
		return legalPerson;
	}

	/**
	 * @param legalPerson the legalPerson to set
	 */
	public void setLegalPerson(SimpleStringProperty legalPerson) {
		this.legalPerson = legalPerson;
	}

	/**
	 * @return the costCenter
	 */
	public SimpleStringProperty getCostCenter() {
		return costCenter;
	}

	/**
	 * @param costCenter the costCenter to set
	 */
	public void setCostCenter(SimpleStringProperty costCenter) {
		this.costCenter = costCenter;
	}

}

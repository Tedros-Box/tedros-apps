/**
 * 
 */
package org.tedros.stock.module.report.model;

import org.tedros.fx.presenter.model.TModelView;
import org.tedros.stock.model.Inventory;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class InventoryTV extends TModelView<Inventory> {

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

}

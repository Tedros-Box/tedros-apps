/**
 * 
 */
package com.tedros.person.module.report.model;

import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.person.report.model.EmployeeItemModel;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class EmployeeItemMV extends TModelView<EmployeeItemModel> {
	
	private SimpleLongProperty id;
	
	private SimpleStringProperty name;
	
	private SimpleStringProperty lastName;
	
	private SimpleStringProperty type;
	
	private SimpleStringProperty employer;

	public EmployeeItemMV(EmployeeItemModel model) {
		super(model);
	}
	
	@Override
	public SimpleStringProperty getDisplayProperty() {
		return null;
	}

	/**
	 * @return the id
	 */
	public SimpleLongProperty getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getType() {
		return type;
	}

	public void setType(SimpleStringProperty type) {
		this.type = type;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getLastName() {
		return lastName;
	}

	public void setLastName(SimpleStringProperty lastName) {
		this.lastName = lastName;
	}

	public SimpleStringProperty getEmployer() {
		return employer;
	}

	public void setEmployer(SimpleStringProperty employer) {
		this.employer = employer;
	}

}

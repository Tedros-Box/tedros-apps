/**
 * 
 */
package org.tedros.person.module.legal.table;

import java.util.Date;
import java.util.Locale;

import org.tedros.person.model.Employee;
import org.tedros.person.model.StaffType;

import org.tedros.core.TLanguage;
import org.tedros.fx.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class EmployeeITemMV extends TEntityModelView<Employee> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty displayProperty;
	
	private SimpleStringProperty name;
	
	private SimpleStringProperty lastName;
	
	private SimpleObjectProperty<StaffType> type;
	
	private SimpleObjectProperty<Date> hiringDate;
	
	private SimpleObjectProperty<Date> resignationDate;
	
	public EmployeeITemMV(Employee entity) {
		super(entity);
		if(TLanguage.getLocale().equals(Locale.ENGLISH))
			super.formatToString("%s, %s", lastName, name);
		else
			super.formatToString("%s %s", name, lastName);
		super.registerProperty("displayProperty", displayProperty);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getDisplayProperty() {
		return displayProperty;
	}

	public void setDisplayProperty(SimpleStringProperty displayProperty) {
		this.displayProperty = displayProperty;
	}
	
	@Override
	public SimpleStringProperty toStringProperty() {
		return this.displayProperty;
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

	public SimpleObjectProperty<StaffType> getType() {
		return type;
	}

	public void setType(SimpleObjectProperty<StaffType> type) {
		this.type = type;
	}

	public SimpleObjectProperty<Date> getHiringDate() {
		return hiringDate;
	}

	public void setHiringDate(SimpleObjectProperty<Date> hiringDate) {
		this.hiringDate = hiringDate;
	}

	public SimpleObjectProperty<Date> getResignationDate() {
		return resignationDate;
	}

	public void setResignationDate(SimpleObjectProperty<Date> resignationDate) {
		this.resignationDate = resignationDate;
	}

}

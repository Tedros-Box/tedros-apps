/**
 * 
 */
package com.tedros.person.module.legal.table;

import java.util.Date;

import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.person.model.LegalPerson;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class LegalPersonItemMV extends TEntityModelView<LegalPerson> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty name;
	
	private SimpleStringProperty otherName;
	
	private SimpleObjectProperty<Date> startActivities;
	
	private SimpleObjectProperty<Date> endActivities;
	
	public LegalPersonItemMV(LegalPerson entity) {
		super(entity);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getDisplayProperty() {
		return name;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getOtherName() {
		return otherName;
	}

	public void setOtherName(SimpleStringProperty otherName) {
		this.otherName = otherName;
	}

	public SimpleObjectProperty<Date> getStartActivities() {
		return startActivities;
	}

	public void setStartActivities(SimpleObjectProperty<Date> startActivities) {
		this.startActivities = startActivities;
	}

	public SimpleObjectProperty<Date> getEndActivities() {
		return endActivities;
	}

	public void setEndActivities(SimpleObjectProperty<Date> endActivities) {
		this.endActivities = endActivities;
	}

}

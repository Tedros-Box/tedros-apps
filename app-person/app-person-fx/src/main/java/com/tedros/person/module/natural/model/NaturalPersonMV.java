/**
 * 
 */
package com.tedros.person.module.natural.model;

import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.person.model.NaturalPerson;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class NaturalPersonMV extends TEntityModelView<NaturalPerson> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty displayProperty;
	
	public NaturalPersonMV(NaturalPerson entity) {
		super(entity);
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

}

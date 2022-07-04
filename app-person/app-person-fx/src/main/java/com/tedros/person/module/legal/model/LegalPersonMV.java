/**
 * 
 */
package com.tedros.person.module.legal.model;

import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.person.model.LegalPerson;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class LegalPersonMV extends TEntityModelView<LegalPerson> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty displayProperty;
	
	public LegalPersonMV(LegalPerson entity) {
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

/**
 * 
 */
package com.tedros.person.module.juridical.model;

import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.person.model.JuridicalType;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class JuridicalTypeMV extends TEntityModelView<JuridicalType> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty displayProperty;
	
	public JuridicalTypeMV(JuridicalType entity) {
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

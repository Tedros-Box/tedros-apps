/**
 * 
 */
package com.tedros.person.module.report.table;

import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.person.report.model.NaturalPersonItemModel;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class NaturalPersonItemMV extends TModelView<NaturalPersonItemModel> {
	
	private SimpleLongProperty id;
	
	private SimpleStringProperty displayProperty;
	
	private SimpleStringProperty name;

	public NaturalPersonItemMV(NaturalPersonItemModel model) {
		super(model);
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
	
	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getDisplayProperty() {
		return displayProperty;
	}

	public void setDisplayProperty(SimpleStringProperty displayProperty) {
		this.displayProperty = displayProperty;
	}

}

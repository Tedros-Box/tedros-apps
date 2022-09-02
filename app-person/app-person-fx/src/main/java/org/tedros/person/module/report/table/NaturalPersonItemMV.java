/**
 * 
 */
package org.tedros.person.module.report.table;

import org.tedros.person.report.model.NaturalPersonItemModel;

import org.tedros.fx.presenter.model.TModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class NaturalPersonItemMV extends TModelView<NaturalPersonItemModel> {
	
	private SimpleLongProperty id;
	
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
}

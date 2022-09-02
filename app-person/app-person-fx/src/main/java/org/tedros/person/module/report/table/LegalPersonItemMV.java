/**
 * 
 */
package org.tedros.person.module.report.table;

import org.tedros.person.report.model.LegalPersonItemModel;

import org.tedros.fx.presenter.model.TModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class LegalPersonItemMV extends TModelView<LegalPersonItemModel> {
	
	private SimpleLongProperty id;
	
	private SimpleStringProperty name;
	
	private SimpleStringProperty otherName;
	
	private SimpleStringProperty type;

	public LegalPersonItemMV(LegalPersonItemModel model) {
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

	public SimpleStringProperty getOtherName() {
		return otherName;
	}

	public void setOtherName(SimpleStringProperty otherName) {
		this.otherName = otherName;
	}

}

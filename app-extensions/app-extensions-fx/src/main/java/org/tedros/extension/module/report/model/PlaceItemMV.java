/**
 * 
 */
package org.tedros.extension.module.report.model;

import org.tedros.extension.report.model.PlaceItemModel;
import org.tedros.fx.model.TModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class PlaceItemMV extends TModelView<PlaceItemModel> {
	
	private SimpleLongProperty id;
	
	private SimpleStringProperty title;
		
	private SimpleStringProperty type;
	
	private SimpleStringProperty country;

	private SimpleStringProperty address;

	public PlaceItemMV(PlaceItemModel model) {
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

	public SimpleStringProperty getTitle() {
		return title;
	}

	public void setTitle(SimpleStringProperty title) {
		this.title = title;
	}

	public SimpleStringProperty getType() {
		return type;
	}

	public void setType(SimpleStringProperty type) {
		this.type = type;
	}

	public SimpleStringProperty getCountry() {
		return country;
	}

	public void setCountry(SimpleStringProperty country) {
		this.country = country;
	}

	public SimpleStringProperty getAddress() {
		return address;
	}

	public void setAddress(SimpleStringProperty address) {
		this.address = address;
	}

}

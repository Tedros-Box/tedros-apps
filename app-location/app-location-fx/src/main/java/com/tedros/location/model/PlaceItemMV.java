/**
 * 
 */
package com.tedros.location.model;

import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;

/**
 * @author Davis Gordon
 *
 */public class PlaceItemMV extends TEntityModelView<Place> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty title;
	
	private SimpleObjectProperty<PlaceType> type;
	
	private SimpleObjectProperty<Address> address;
	
	
	public PlaceItemMV(Place entity) {
		super(entity);
		
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleObjectProperty<PlaceType> getType() {
		return type;
	}

	public void setType(SimpleObjectProperty<PlaceType> type) {
		this.type = type;
	}

	public SimpleObjectProperty<Address> getAddress() {
		return address;
	}

	public void setAddress(SimpleObjectProperty<Address> address) {
		this.address = address;
	}

	public SimpleStringProperty getDisplayProperty() {
		return title;
	}

	public SimpleStringProperty getTitle() {
		return title;
	}

	public void setTitle(SimpleStringProperty title) {
		this.title = title;
	}



}

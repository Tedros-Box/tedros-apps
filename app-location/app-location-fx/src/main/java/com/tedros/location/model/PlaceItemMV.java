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
	
	private SimpleStringProperty displayProperty;
	
	private SimpleObjectProperty<PlaceType> type;
	
	private SimpleObjectProperty<Address> address;
	
	
	public PlaceItemMV(Place entity) {
		super(entity);
		ChangeListener<PlaceType> chl = (a,b,n)->{
			displayProperty.setValue(n!=null?n.getName():"");
		};
		super.getListenerRepository().add("type01", chl);
		type.addListener(new WeakChangeListener<>(chl));
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
		return displayProperty;
	}

	public void setDisplayProperty(SimpleStringProperty displayProperty) {
		this.displayProperty = displayProperty;
	}


}

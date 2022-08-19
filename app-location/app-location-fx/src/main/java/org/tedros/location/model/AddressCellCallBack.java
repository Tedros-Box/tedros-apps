package com.tedros.location.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class AddressCellCallBack implements Callback <CellDataFeatures<PlaceItemMV, ObservableValue<Address>>, ObservableValue<String>> {
	@Override
	public ObservableValue<String> call(CellDataFeatures<PlaceItemMV, ObservableValue<Address>> param) {
		Address d = param.getValue().getAddress().getValue();
		return (d!=null) 
				? new SimpleStringProperty(d.toString())
						: new SimpleStringProperty();
	}
	
	
	
}

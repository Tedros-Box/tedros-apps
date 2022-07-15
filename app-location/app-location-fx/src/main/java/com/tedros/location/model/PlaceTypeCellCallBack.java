package com.tedros.location.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class PlaceTypeCellCallBack implements Callback <CellDataFeatures<PlaceItemMV, ObservableValue<PlaceType>>, ObservableValue<String>> {
	@Override
	public ObservableValue<String> call(CellDataFeatures<PlaceItemMV, ObservableValue<PlaceType>> param) {
		PlaceType d = param.getValue().getType().getValue();
		return (d!=null) 
				? new SimpleStringProperty(d.getName())
						: new SimpleStringProperty();
	}
	
	
	
}

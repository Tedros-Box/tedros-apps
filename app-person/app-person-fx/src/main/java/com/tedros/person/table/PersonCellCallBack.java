package com.tedros.person.table;

import com.tedros.core.TLanguage;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class PersonCellCallBack implements Callback <CellDataFeatures<PersonItemMV, ObservableValue<String>>, ObservableValue<String>> {
	@Override
	public ObservableValue<String> call(CellDataFeatures<PersonItemMV, ObservableValue<String>> param) {
		String s = param.getValue().getModel().toString();
		return new SimpleStringProperty(TLanguage.getInstance().getString(s));
	}
	
	
	
}

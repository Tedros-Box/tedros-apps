package org.tedros.person.module.legal.table;

import java.util.Date;

import org.tedros.person.model.StaffType;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class StaffTypeCellCallBack implements Callback <CellDataFeatures<EmployeeITemMV, ObservableValue<Date>>, ObservableValue<String>> {
	@Override
	public ObservableValue<String> call(CellDataFeatures<EmployeeITemMV, ObservableValue<Date>> param) {
		StaffType d = param.getValue().getType().getValue();
		return (d!=null) 
				? new SimpleStringProperty(d.getName())
						: new SimpleStringProperty();
	}
	
	
	
}

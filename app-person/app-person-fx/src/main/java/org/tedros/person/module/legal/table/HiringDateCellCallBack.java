package org.tedros.person.module.legal.table;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class HiringDateCellCallBack implements Callback <CellDataFeatures<EmployeeITemMV, ObservableValue<Date>>, ObservableValue<String>> {
	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	@Override
	public ObservableValue<String> call(CellDataFeatures<EmployeeITemMV, ObservableValue<Date>> param) {
		Date d = param.getValue().getHiringDate().getValue();
		return (d!=null) 
				? new SimpleStringProperty(format.format(d))
						: new SimpleStringProperty();
	}
	
	
	
}

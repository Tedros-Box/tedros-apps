package org.tedros.person.module.legal.table;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class EndActivitiesCellCallBack implements Callback <CellDataFeatures<LegalPersonItemMV, ObservableValue<Date>>, ObservableValue<String>> {
	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	@Override
	public ObservableValue<String> call(CellDataFeatures<LegalPersonItemMV, ObservableValue<Date>> param) {
		Date d = param.getValue().getEndActivities().getValue();
		return (d!=null) 
				? new SimpleStringProperty(format.format(d))
						: new SimpleStringProperty();
	}
	
	
	
}

package com.tedros.services.module.plan.table;

import com.tedros.core.TLanguage;
import com.tedros.services.domain.Status;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class StatusCellCallBack implements Callback <CellDataFeatures<PlanItemMV, ObservableValue<Status>>, ObservableValue<String>> {
	@Override
	public ObservableValue<String> call(CellDataFeatures<PlanItemMV, ObservableValue<Status>> param) {
		Status s = param.getValue().getStatus().getValue();
		if(s!=null)
			return new SimpleStringProperty(TLanguage.getInstance().getString(s.getValue()));
		return new SimpleStringProperty("");
	}
	
	
	
}

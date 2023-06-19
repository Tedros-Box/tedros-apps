package org.tedros.samples.module.control.model;

import java.util.Date;

import org.tedros.fx.model.TModelView;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class MasterMV extends TModelView<MasterModel> {

	SimpleStringProperty string0;
	
	SimpleIntegerProperty integer0;
	
	SimpleLongProperty long0;
	
	SimpleDoubleProperty double0;
	
	SimpleObjectProperty<Date>  date0;
	
	SimpleObjectProperty<DetailModel> detail0;

	
	public MasterMV(MasterModel model) {
		super(model);
	}

}

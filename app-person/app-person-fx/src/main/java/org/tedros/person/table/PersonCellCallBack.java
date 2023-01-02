package org.tedros.person.table;

import org.apache.commons.lang3.StringUtils;
import org.tedros.core.TLanguage;
import org.tedros.person.model.Person;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class PersonCellCallBack 
implements Callback <CellDataFeatures<PersonItemMV, ObservableValue<String>>, ObservableValue<String>> {
	@Override
	public ObservableValue<String> call(CellDataFeatures<PersonItemMV, ObservableValue<String>> param) {
		Person p = param.getValue().getModel();
		String s = StringUtils.isNotBlank(p.getDiscriminatorDesc()) 
				? "[" + p.getDiscriminatorDesc() + "] " + p.toString()
				: p.toString();
		return new SimpleStringProperty(TLanguage.getInstance().getString(s));
	}
	
	
	
}

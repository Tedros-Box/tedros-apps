/**
 * 
 */
package org.tedros.person.module.company.table;

import java.util.Date;

import org.tedros.fx.model.TEntityModelView;
import org.tedros.person.model.CostCenter;
import org.tedros.person.table.PersonTV;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class CostCenterTV extends TEntityModelView<CostCenter> {

	private SimpleStringProperty code;
	
	private SimpleStringProperty name;
	
	private SimpleObjectProperty<PersonTV> responsable;
	
	private SimpleObjectProperty<Date> openingDate;

	private SimpleObjectProperty<Date> closingDate;
	
	public CostCenterTV(CostCenter entity) {
		super(entity);
		super.formatToString("%s %s", code, name);
	}

	public SimpleStringProperty getCode() {
		return code;
	}

	public void setCode(SimpleStringProperty code) {
		this.code = code;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleObjectProperty<PersonTV> getResponsable() {
		return responsable;
	}

	public void setResponsable(SimpleObjectProperty<PersonTV> responsable) {
		this.responsable = responsable;
	}

	public SimpleObjectProperty<Date> getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(SimpleObjectProperty<Date> openingDate) {
		this.openingDate = openingDate;
	}

	public SimpleObjectProperty<Date> getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(SimpleObjectProperty<Date> closingDate) {
		this.closingDate = closingDate;
	}

}

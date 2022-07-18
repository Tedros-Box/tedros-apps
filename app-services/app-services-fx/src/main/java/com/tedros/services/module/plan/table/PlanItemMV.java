/**
 * 
 */
package com.tedros.services.module.plan.table;

import java.math.BigDecimal;
import java.util.Date;

import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.services.domain.Status;
import com.tedros.services.model.Plan;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */public class PlanItemMV extends TEntityModelView<Plan> {

	private SimpleLongProperty id;

	private SimpleStringProperty name;
	
	private SimpleObjectProperty<BigDecimal> registrationFee;
	
	private SimpleObjectProperty<BigDecimal> value;
	
	private SimpleObjectProperty<Date> beginDate;
	
	private SimpleObjectProperty<Date> endDate;

	private SimpleObjectProperty<Status> status;
	
	public PlanItemMV(Plan entity) {
		super(entity);
	}


	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleObjectProperty<BigDecimal> getRegistrationFee() {
		return registrationFee;
	}

	public void setRegistrationFee(SimpleObjectProperty<BigDecimal> registrationFee) {
		this.registrationFee = registrationFee;
	}

	public SimpleObjectProperty<BigDecimal> getValue() {
		return value;
	}

	public void setValue(SimpleObjectProperty<BigDecimal> value) {
		this.value = value;
	}

	public SimpleObjectProperty<Date> getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(SimpleObjectProperty<Date> beginDate) {
		this.beginDate = beginDate;
	}

	public SimpleObjectProperty<Date> getEndDate() {
		return endDate;
	}

	public void setEndDate(SimpleObjectProperty<Date> endDate) {
		this.endDate = endDate;
	}

	public SimpleObjectProperty<Status> getStatus() {
		return status;
	}

	public void setStatus(SimpleObjectProperty<Status> status) {
		this.status = status;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return name;
	}

}

/**
 * 
 */
package com.tedros.services.module.plan.model;

import com.tedros.fxapi.TUsualKey;
import com.tedros.fxapi.annotation.control.TDoubleField;
import com.tedros.fxapi.annotation.control.TIntegerField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.presenter.TEditModalPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.domain.TZeroValidation;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.services.ejb.controller.IPaymentPlanController;
import com.tedros.services.model.PaymentPlan;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
@TEditModalPresenter(listViewMinWidth=150, listViewMaxWidth=150)
@TEjbService(model = PaymentPlan.class, serviceName = IPaymentPlanController.JNDI_NAME)
public class PaymentPlanMV extends TEntityModelView<PaymentPlan> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty displayProperty;
	
	@TLabel(text=TUsualKey.AMOUNT)
	@TIntegerField(zeroValidation=TZeroValidation.GREATHER_THAN_ZERO)
	private SimpleIntegerProperty amount;
	
	@TLabel(text=TUsualKey.DISCOUNT)
	@TDoubleField
	private SimpleDoubleProperty discount;
	
	public PaymentPlanMV(PaymentPlan entity) {
		super(entity);
		super.formatFieldsToDisplay("%d > %.2f%%", amount, discount);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getDisplayProperty() {
		return displayProperty;
	}

	public void setDisplayProperty(SimpleStringProperty displayProperty) {
		this.displayProperty = displayProperty;
	}

	public SimpleIntegerProperty getAmount() {
		return amount;
	}

	public void setAmount(SimpleIntegerProperty amount) {
		this.amount = amount;
	}

	public SimpleDoubleProperty getDiscount() {
		return discount;
	}

	public void setDiscount(SimpleDoubleProperty discount) {
		this.discount = discount;
	}

}

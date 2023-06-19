/**
 * 
 */
package org.tedros.services.module.plan.model;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TDoubleField;
import org.tedros.fx.annotation.control.TIntegerField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.presenter.TEditModalPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.domain.TValidateNumber;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.services.ejb.controller.IPaymentPlanController;
import org.tedros.services.model.PaymentPlan;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * @author Davis Gordon
 *
 */
@TEditModalPresenter(listViewMinWidth=150, listViewMaxWidth=150)
@TEjbService(model = PaymentPlan.class, serviceName = IPaymentPlanController.JNDI_NAME)
public class PaymentPlanMV extends TEntityModelView<PaymentPlan> {

	@TLabel(text=TUsualKey.AMOUNT)
	@TIntegerField(validate=TValidateNumber.GREATHER_THAN_ZERO)
	private SimpleIntegerProperty amount;
	
	@TLabel(text=TUsualKey.DISCOUNT)
	@TDoubleField
	private SimpleDoubleProperty discount;
	
	public PaymentPlanMV(PaymentPlan entity) {
		super(entity);
		super.formatToString("%d > %.2f%%", amount, discount);
	}
}

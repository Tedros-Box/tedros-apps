/**
 * 
 */
package com.tedros.services.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.services.model.PaymentPlan;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IPaymentPlanController extends ITSecureEjbController<PaymentPlan> {

	static final String JNDI_NAME = "IPaymentPlanControllerRemote";
		
}

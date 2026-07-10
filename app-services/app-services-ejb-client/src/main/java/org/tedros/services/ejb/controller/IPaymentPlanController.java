/**
 * 
 */
package org.tedros.services.ejb.controller;

import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.services.model.PaymentPlan;

import jakarta.ejb.Remote;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IPaymentPlanController extends ITSecureEjbController<PaymentPlan> {

	static final String JNDI_NAME = "IPaymentPlanControllerRemote";
		
}

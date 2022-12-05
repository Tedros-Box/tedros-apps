/**
 * 
 */
package org.tedros.services.ejb.controller;

import javax.ejb.Remote;

import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.services.model.PaymentPlan;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IPaymentPlanController extends ITSecureEjbController<PaymentPlan> {

	static final String JNDI_NAME = "IPaymentPlanControllerRemote";
		
}

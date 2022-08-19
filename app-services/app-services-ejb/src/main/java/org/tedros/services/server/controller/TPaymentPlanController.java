/**
 * 
 */
package org.tedros.services.server.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.services.ejb.controller.IPaymentPlanController;
import org.tedros.services.model.PaymentPlan;
import org.tedros.services.server.base.service.TServService;

import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

/**
 * @author Davis Gordon
 *
 */
@TSecurityInterceptor
@Stateless(name="IPaymentPlanController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TPaymentPlanController extends TSecureEjbController<PaymentPlan> 
implements IPaymentPlanController, ITSecurity  {

	@EJB
	private TServService<PaymentPlan> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<PaymentPlan> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}

/**
 * 
 */
package com.tedros.services.server.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TSecurityInterceptor;
import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.services.ejb.controller.IPaymentPlanController;
import com.tedros.services.model.PaymentPlan;
import com.tedros.services.server.base.service.TServService;

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

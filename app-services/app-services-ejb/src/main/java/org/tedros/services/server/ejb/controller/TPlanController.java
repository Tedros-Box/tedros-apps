/**
 * 
 */
package org.tedros.services.server.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;
import org.tedros.services.domain.DomainApp;
import org.tedros.services.ejb.controller.IPlanController;
import org.tedros.services.model.Plan;
import org.tedros.services.server.ejb.service.TServService;

/**
 * @author Davis Gordon
 *
 */
@TSecurityInterceptor
@Stateless(name="IPlanController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.PLAN_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TPlanController extends TSecureEjbController<Plan> implements IPlanController, ITSecurity  {

	@EJB
	private TServService<Plan> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<Plan> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}

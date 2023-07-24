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
import org.tedros.services.ejb.controller.IServiceTypeController;
import org.tedros.services.model.ServiceType;
import org.tedros.services.server.ejb.service.TServService;

/**
 * @author Davis Gordon
 *
 */
@TSecurityInterceptor
@Stateless(name="IServiceTypeController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.SERVICE_TYPE_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TServiceTypeController extends TSecureEjbController<ServiceType> implements IServiceTypeController, ITSecurity  {

	@EJB
	private TServService<ServiceType> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<ServiceType> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}

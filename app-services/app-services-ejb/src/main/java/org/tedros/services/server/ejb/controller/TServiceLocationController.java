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
import org.tedros.services.ejb.controller.IServiceLocationController;
import org.tedros.services.model.ServiceLocation;
import org.tedros.services.server.ejb.service.TServService;

/**
 * @author Davis Gordon
 *
 */
@TSecurityInterceptor
@Stateless(name="IServiceLocationController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.SERVICE_LOCATION_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TServiceLocationController extends TSecureEjbController<ServiceLocation> 
implements IServiceLocationController, ITSecurity  {

	@EJB
	private TServService<ServiceLocation> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<ServiceLocation> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}

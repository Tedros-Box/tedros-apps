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
import com.tedros.ejb.base.security.TAccessPolicie;
import com.tedros.ejb.base.security.TBeanPolicie;
import com.tedros.ejb.base.security.TBeanSecurity;
import com.tedros.ejb.base.security.TSecurityInterceptor;
import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.services.domain.DomainApp;
import com.tedros.services.ejb.controller.IServiceLocationController;
import com.tedros.services.model.ServiceLocation;
import com.tedros.services.server.base.service.TServService;

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

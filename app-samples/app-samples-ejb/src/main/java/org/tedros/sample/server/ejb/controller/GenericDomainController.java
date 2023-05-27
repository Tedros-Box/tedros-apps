/**
 * 
 */
package org.tedros.sample.server.ejb.controller;

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
import org.tedros.sample.domain.DomainApp;
import org.tedros.sample.ejb.controller.IGenericDomainController;
import org.tedros.sample.entity.GenericDomain;
import org.tedros.sample.server.ejb.service.SmplsService;

/**
 * The controller bean
 * 
 * @author Davis
 *
 */
@TSecurityInterceptor
@Stateless(name="IGenericDomainController")
@TBeanSecurity({
	@TBeanPolicie(id = DomainApp.ORDER_STATUS_FORM_ID, 
	policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS }),
	@TBeanPolicie(id = DomainApp.SALE_STATUS_FORM_ID, 
	policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS }),
	@TBeanPolicie(id = DomainApp.SALE_TYPE_FORM_ID, 
	policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })
})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class GenericDomainController extends TSecureEjbController<GenericDomain> implements IGenericDomainController, ITSecurity  {

	@EJB
	private SmplsService<GenericDomain> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<GenericDomain> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}

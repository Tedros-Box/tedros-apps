/**
 * 
 */
package org.tedros.sample.server.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.sample.domain.DomainApp;
import org.tedros.sample.ejb.controller.ISaleEventConfigController;
import org.tedros.sample.entity.SaleEventConfig;
import org.tedros.sample.server.ejb.service.SmplsService;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

/**
 * The controller bean
 * 
 * @author Davis
 *
 */
@TSecurityInterceptor
@Stateless(name="ISaleEventConfigController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.SALE_EVENT_CONFIG_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SaleEventConfigController extends TSecureEjbController<SaleEventConfig> implements ISaleEventConfigController, ITSecurity  {

	@EJB
	private SmplsService<SaleEventConfig> serv;
	
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<SaleEventConfig> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
	
}

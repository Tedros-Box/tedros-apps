/**
 * 
 */
package org.tedros.it.tools.ejb.controller;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;
import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.ejb.controller.IRedmineConfigController;
import org.tedros.it.tools.ejb.service.RedmineService;
import org.tedros.it.tools.entity.RedmineConfig;

/**
 * The controller bean
 * 
 * @author Davis Dun
 *
 */
@TSecurityInterceptor
@Stateless(name="IRedmineConfigController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.REDMINE_CONFIG_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TRedmineConfigController extends TSecureEjbController<RedmineConfig> implements IRedmineConfigController, ITSecurity  {

	@EJB
	private RedmineService<RedmineConfig> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<RedmineConfig> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}

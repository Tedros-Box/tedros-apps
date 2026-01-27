/**
 * 
 */
package org.tedros.it.tools.ejb.controller;

import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.ejb.service.ItSupportToolsService;
import org.tedros.it.tools.entity.Gmud;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

/**
 * The controller bean
 * 
 * @author Davis Dun
 *
 */
@TSecurityInterceptor
@Stateless(name="IGmudController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.CHANGE_MANAGER_GMUD_EDIT_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class GmudController extends TSecureEjbController<Gmud> implements IGmudController, ITSecurity  {

	@EJB
	private ItSupportToolsService<Gmud> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<Gmud> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}

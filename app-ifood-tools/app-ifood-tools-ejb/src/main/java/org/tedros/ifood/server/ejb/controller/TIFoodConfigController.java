/**
 * 
 */
package org.tedros.ifood.server.ejb.controller;

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
import org.tedros.ifood.domain.DomainApp;
import org.tedros.ifood.ejb.controller.IIFoodConfigController;
import org.tedros.ifood.entity.IFoodConfig;
import org.tedros.ifood.server.ejb.service.IFOODService;

/**
 * The controller bean
 * 
 * @author Davis Dun
 *
 */
@TSecurityInterceptor
@Stateless(name="IIFoodConfigController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.IFOOD_CONFIG__FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TIFoodConfigController extends TSecureEjbController<IFoodConfig> implements IIFoodConfigController, ITSecurity  {

	@EJB
	private IFOODService<IFoodConfig> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<IFoodConfig> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}

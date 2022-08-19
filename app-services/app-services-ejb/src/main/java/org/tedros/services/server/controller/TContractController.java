/**
 * 
 */
package org.tedros.services.server.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.services.domain.DomainApp;
import org.tedros.services.ejb.controller.IContractController;
import org.tedros.services.model.Contract;
import org.tedros.services.server.base.service.TServService;

import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

/**
 * @author Davis Gordon
 *
 */
@TSecurityInterceptor
@Stateless(name="IContractController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.CONTRACT_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TContractController extends TSecureEjbController<Contract> implements IContractController, ITSecurity  {

	@EJB
	private TServService<Contract> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<Contract> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}

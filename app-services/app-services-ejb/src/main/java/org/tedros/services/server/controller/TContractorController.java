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
import com.tedros.services.ejb.controller.IContractorController;
import com.tedros.services.model.Contractor;
import com.tedros.services.server.base.service.TServService;

/**
 * @author Davis Gordon
 *
 */
@TSecurityInterceptor
@Stateless(name="IContractorController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.CONTRACTOR_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TContractorController extends TSecureEjbController<Contractor> implements IContractorController, ITSecurity  {

	@EJB
	private TServService<Contractor> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<Contractor> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}

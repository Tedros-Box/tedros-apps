/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.tedros.server.location.controller;

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
import com.tedros.ejb.controller.IStreetTypeController;
import com.tedros.location.domain.DomainApp;
import com.tedros.location.model.StreetType;
import com.tedros.server.base.service.TLocService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@TSecurityInterceptor
@Stateless(name="IStreetTypeController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.STREET_TYPE_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class StreetTypeController extends TSecureEjbController<StreetType> implements IStreetTypeController, ITSecurity {
	
	@EJB
	private TLocService<StreetType> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<StreetType> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
		

}

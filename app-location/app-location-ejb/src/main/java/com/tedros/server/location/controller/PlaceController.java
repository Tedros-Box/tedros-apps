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
import com.tedros.ejb.controller.IPlaceController;
import com.tedros.location.domain.DomainApp;
import com.tedros.location.model.Place;
import com.tedros.server.base.service.TStatelessService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@TSecurityInterceptor
@Stateless(name="IPlaceController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.PLACE_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class PlaceController extends TSecureEjbController<Place> implements IPlaceController, ITSecurity {
	
	@EJB
	private TStatelessService<Place> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<Place> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
		

}

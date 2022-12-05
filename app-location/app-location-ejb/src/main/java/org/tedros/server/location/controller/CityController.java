/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.tedros.server.location.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.ejb.controller.ICityController;
import org.tedros.location.domain.DomainApp;
import org.tedros.location.model.AdminArea;
import org.tedros.location.model.City;
import org.tedros.location.model.Country;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.location.service.CityService;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@TSecurityInterceptor
@Stateless(name="ICityController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.CITY_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class CityController extends TSecureEjbController<City> implements ICityController, ITSecurity {
	
	@EJB
	private CityService serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<City> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	@Override
	public TResult<List<City>> filter(TAccessToken token, Country country, AdminArea adminArea) {
		try {
			return new TResult<>(TState.SUCCESS, serv.filter(country, adminArea));
		} catch (Exception e) {
			return super.processException(token, null, e);
		}
	}
		

}

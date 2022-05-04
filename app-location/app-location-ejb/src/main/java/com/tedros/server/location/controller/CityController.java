/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.tedros.server.location.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessPolicie;
import com.tedros.ejb.base.security.TAccessToken;
import com.tedros.ejb.base.security.TBeanPolicie;
import com.tedros.ejb.base.security.TBeanSecurity;
import com.tedros.ejb.base.security.TSecurityInterceptor;
import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.ejb.controller.ICityController;
import com.tedros.location.domain.DomainApp;
import com.tedros.location.model.AdminArea;
import com.tedros.location.model.City;
import com.tedros.location.model.Country;
import com.tedros.server.location.service.CityService;

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
			return new TResult<>(EnumResult.SUCESS, serv.filter(country, adminArea));
		} catch (Exception e) {
			return super.processException(token, null, e);
		}
	}
		

}

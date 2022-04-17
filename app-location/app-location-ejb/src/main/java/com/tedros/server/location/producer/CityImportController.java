/**
 * 
 */
package com.tedros.server.location.producer;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TEjbImportController;
import com.tedros.ejb.base.security.TSecurityInterceptor;
import com.tedros.ejb.base.service.ITEjbImportService;
import com.tedros.ejb.controller.ICityImportController;
import com.tedros.location.model.City;
import com.tedros.server.location.service.CityImportService;

/**
 * @author Davis Gordon
 *
 */
@TSecurityInterceptor
@Stateless(name="ICityImportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class CityImportController extends TEjbImportController<City> implements ICityImportController {

	@EJB
	private CityImportService serv;
	
	@EJB
	private ITSecurityController security;
	
	@Override
	public ITEjbImportService<City> getService() {
		return serv;
	}

	@Override
	public ITSecurityController getSecurityController() {
		return security;
	}

	

}

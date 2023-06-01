/**
 * 
 */
package org.tedros.extension.server.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.extension.ejb.controller.ICityImportController;
import org.tedros.extension.model.City;
import org.tedros.extension.server.ejb.service.CityImportService;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TEjbImportController;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbImportService;

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

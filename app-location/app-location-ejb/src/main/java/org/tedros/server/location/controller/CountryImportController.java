/**
 * 
 */
package org.tedros.server.location.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.ejb.controller.ICountryImportController;
import org.tedros.location.model.Country;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TEjbImportController;
import org.tedros.server.location.service.CountryImportService;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbImportService;

/**
 * @author Davis Gordon
 *
 */
@TSecurityInterceptor
@Stateless(name="ICountryImportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class CountryImportController extends TEjbImportController<Country> implements ICountryImportController {

	@EJB
	private CountryImportService serv;
	
	@EJB
	private ITSecurityController security;
	
	@Override
	public ITEjbImportService<Country> getService() {
		return serv;
	}

	@Override
	public ITSecurityController getSecurityController() {
		return security;
	}

	

}

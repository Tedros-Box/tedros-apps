/**
 * 
 */
package org.tedros.extension.server.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.extension.ejb.controller.ICountryImportController;
import org.tedros.extension.model.Country;
import org.tedros.extension.server.ejb.service.CountryImportService;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TEjbImportController;
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

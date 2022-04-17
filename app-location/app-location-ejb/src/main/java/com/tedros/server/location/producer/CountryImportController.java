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
import com.tedros.ejb.controller.ICountryImportController;
import com.tedros.location.model.Country;
import com.tedros.server.location.service.CountryImportService;

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

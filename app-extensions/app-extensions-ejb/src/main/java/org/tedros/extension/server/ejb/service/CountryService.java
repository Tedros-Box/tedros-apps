/**
 * 
 */
package org.tedros.extension.server.ejb.service;

import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import org.tedros.extension.model.Country;
import org.tedros.extension.server.cdi.bo.CountryBO;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class CountryService extends TEjbService<Country> {

	@Inject
	private CountryBO bo;
	
	@Override
	public ITGenericBO<Country> getBussinesObject() {
		return bo;
	}

}

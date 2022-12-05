/**
 * 
 */
package org.tedros.server.location.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.location.model.Country;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;
import org.tedros.server.location.bo.CountryBO;

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

/**
 * 
 */
package org.tedros.extension.server.ejb.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

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

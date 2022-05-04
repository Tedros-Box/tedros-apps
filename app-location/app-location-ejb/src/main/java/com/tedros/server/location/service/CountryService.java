/**
 * 
 */
package com.tedros.server.location.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.location.model.Country;
import com.tedros.server.location.bo.CountryBO;

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

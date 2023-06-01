/**
 * 
 */
package org.tedros.extension.server.ejb.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.extension.model.AdminArea;
import org.tedros.extension.model.City;
import org.tedros.extension.model.Country;
import org.tedros.extension.server.cdi.bo.CityBO;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class CityService extends TEjbService<City> {

	@Inject
	private CityBO bo;
	
	@Override
	public ITGenericBO<City> getBussinesObject() {
		return bo;
	}
	
	public List<City> filter(Country country, AdminArea adminArea){
		return bo.filter(country, adminArea);
	}

}

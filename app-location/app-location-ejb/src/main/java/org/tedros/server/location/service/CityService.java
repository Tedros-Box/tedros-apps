/**
 * 
 */
package org.tedros.server.location.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.location.model.AdminArea;
import org.tedros.location.model.City;
import org.tedros.location.model.Country;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;
import org.tedros.server.location.bo.CityBO;

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

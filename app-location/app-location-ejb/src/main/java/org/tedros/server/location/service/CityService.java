/**
 * 
 */
package com.tedros.server.location.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.location.model.AdminArea;
import com.tedros.location.model.City;
import com.tedros.location.model.Country;
import com.tedros.server.location.bo.CityBO;

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

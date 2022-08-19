/**
 * 
 */
package com.tedros.server.location.bo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.location.model.AdminArea;
import com.tedros.location.model.City;
import com.tedros.location.model.Country;
import com.tedros.server.location.eao.CityEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class CityBO extends TGenericBO<City> {

	@Inject
	private CityEAO eao;
	
	@Override
	public ITGenericEAO<City> getEao() {
		return eao;
	}
	
	public List<City> filter(Country country, AdminArea adminArea){
		return eao.filter(country, adminArea);
	}

}

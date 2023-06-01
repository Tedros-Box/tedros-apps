/**
 * 
 */
package org.tedros.extension.server.cdi.bo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.extension.model.AdminArea;
import org.tedros.extension.model.City;
import org.tedros.extension.model.Country;
import org.tedros.extension.server.cdi.eao.CityEAO;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;

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

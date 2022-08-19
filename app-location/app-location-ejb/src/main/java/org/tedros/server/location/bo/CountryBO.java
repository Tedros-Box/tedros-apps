/**
 * 
 */
package com.tedros.server.location.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.location.model.Country;
import com.tedros.server.location.eao.CountryEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class CountryBO extends TGenericBO<Country> {

	@Inject
	private CountryEAO eao;
	
	@Override
	public ITGenericEAO<Country> getEao() {
		return eao;
	}

}

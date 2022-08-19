/**
 * 
 */
package org.tedros.server.location.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.location.model.Country;
import org.tedros.server.location.eao.CountryEAO;

import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;

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

/**
 * 
 */
package org.tedros.server.location.bo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.location.model.AdminArea;
import org.tedros.location.model.Country;
import org.tedros.server.location.eao.AdminAreaEAO;

import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class AdminAreaBO extends TGenericBO<AdminArea> {

	@Inject
	private AdminAreaEAO eao;
	
	@Override
	public ITGenericEAO<AdminArea> getEao() {
		return eao;
	}
	
	public List<AdminArea> filter(Country country){
		return eao.filter(country);
	}

}

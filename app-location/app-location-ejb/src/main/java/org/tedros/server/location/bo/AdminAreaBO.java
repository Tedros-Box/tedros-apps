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
import com.tedros.location.model.Country;
import com.tedros.server.location.eao.AdminAreaEAO;

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

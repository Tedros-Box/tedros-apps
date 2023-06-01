/**
 * 
 */
package org.tedros.extension.server.cdi.bo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.extension.model.AdminArea;
import org.tedros.extension.model.Country;
import org.tedros.extension.server.cdi.eao.AdminAreaEAO;
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

/**
 * 
 */
package org.tedros.it.tools.cdi.bo;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import org.tedros.it.tools.cdi.eao.RedmineEAO;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.entity.ITEntity;

/**
 * The CDI business object 
 * 
 * @author Davis Dun
 *
 */
@Dependent
public class RedmineBO<E extends ITEntity> extends TGenericBO<E> {

	@Inject
	private RedmineEAO<E> eao;
	
	@Override
	public ITGenericEAO<E> getEao() {
		return eao;
	}

}

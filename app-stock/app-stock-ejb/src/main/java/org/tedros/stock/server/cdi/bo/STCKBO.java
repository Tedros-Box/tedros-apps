/**
 * 
 */
package org.tedros.stock.server.cdi.bo;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.entity.ITEntity;

import org.tedros.stock.server.cdi.eao.STCKEAO;

/**
 * The CDI business object 
 * 
 * @author Davis Dun
 *
 */
@Dependent
public class STCKBO<E extends ITEntity> extends TGenericBO<E> {

	@Inject
	private STCKEAO<E> eao;
	
	@Override
	public ITGenericEAO<E> getEao() {
		return eao;
	}

}

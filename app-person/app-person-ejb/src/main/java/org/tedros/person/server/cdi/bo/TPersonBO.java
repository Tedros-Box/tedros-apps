/**
 * 
 */
package org.tedros.person.server.cdi.bo;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.tedros.person.server.cdi.eao.TPersonEAO;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.entity.ITEntity;

/**
 * @author Davis Gordon
 *
 */
@Dependent
public class TPersonBO<E extends ITEntity> extends TGenericBO<E> {

	@Inject
	private TPersonEAO<E> eao;
	
	@Override
	public ITGenericEAO<E> getEao() {
		return eao;
	}

}

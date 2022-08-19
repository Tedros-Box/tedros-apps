/**
 * 
 */
package org.tedros.server.base.bo;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.tedros.server.base.eao.TLocEAO;

import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.entity.ITEntity;

/**
 * @author Davis Gordon
 *
 */
@Dependent
public class TLocBO<E extends ITEntity> extends TGenericBO<E> {

	@Inject
	private TLocEAO<E> eao;
	
	@Override
	public ITGenericEAO<E> getEao() {
		return eao;
	}

}

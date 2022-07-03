/**
 * 
 */
package com.tedros.person.server.base.bo;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.person.server.base.eao.TPersonEAO;

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

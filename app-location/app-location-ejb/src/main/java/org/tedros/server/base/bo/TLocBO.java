/**
 * 
 */
package com.tedros.server.base.bo;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.server.base.eao.TLocEAO;

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

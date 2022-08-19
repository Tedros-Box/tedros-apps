/**
 * 
 */
package com.tedros.services.server.base.bo;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.services.server.base.eao.TServEAO;

/**
 * @author Davis Gordon
 *
 */
@Dependent
public class TServBO<E extends ITEntity> extends TGenericBO<E> {

	@Inject
	private TServEAO<E> eao;
	
	@Override
	public ITGenericEAO<E> getEao() {
		return eao;
	}

}

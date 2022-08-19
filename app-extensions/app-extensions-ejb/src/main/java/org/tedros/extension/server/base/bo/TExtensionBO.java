/**
 * 
 */
package com.tedros.extension.server.base.bo;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.extension.server.base.eao.TExtensionEAO;

/**
 * @author Davis Gordon
 *
 */
@Dependent
public class TExtensionBO<E extends ITEntity> extends TGenericBO<E> {

	@Inject
	private TExtensionEAO<E> eao;
	
	@Override
	public ITGenericEAO<E> getEao() {
		return eao;
	}

}

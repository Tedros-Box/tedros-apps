/**
 * 
 */
package com.tedros.docs.server.base.bo;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.docs.server.base.eao.TDocsEAO;

/**
 * @author Davis Gordon
 *
 */
@Dependent
public class TDocsBO<E extends ITEntity> extends TGenericBO<E> {

	@Inject
	private TDocsEAO<E> eao;
	
	@Override
	public ITGenericEAO<E> getEao() {
		return eao;
	}

}

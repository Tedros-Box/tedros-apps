/**
 * 
 */
package com.template.server.base.bo;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.ejb.base.entity.ITEntity;
import com.template.server.base.eao.TMP_EAO;

/**
 * The CDI business object 
 * 
 * @author myname
 *
 */
@Dependent
public class TMP_BO<E extends ITEntity> extends TGenericBO<E> {

	@Inject
	private TMP_EAO<E> eao;
	
	@Override
	public ITGenericEAO<E> getEao() {
		return eao;
	}

}

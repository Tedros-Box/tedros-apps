/**
 * 
 */
package com.template.server.cdi.bo;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.entity.ITEntity;

import com.template.server.cdi.eao.TMP_EAO;

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

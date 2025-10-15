/**
 * 
 */
package org.tedros.server.cdi.bo;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.entity.ITEntity;

import org.tedros.server.cdi.eao.RDMN_EAO;

/**
 * The CDI business object 
 * 
 * @author Davis Dun
 *
 */
@Dependent
public class RDMN_BO<E extends ITEntity> extends TGenericBO<E> {

	@Inject
	private RDMN_EAO<E> eao;
	
	@Override
	public ITGenericEAO<E> getEao() {
		return eao;
	}

}

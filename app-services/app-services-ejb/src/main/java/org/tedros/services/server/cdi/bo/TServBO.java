/**
 * 
 */
package org.tedros.services.server.cdi.bo;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.entity.ITEntity;
import org.tedros.services.server.cdi.eao.TServEAO;

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

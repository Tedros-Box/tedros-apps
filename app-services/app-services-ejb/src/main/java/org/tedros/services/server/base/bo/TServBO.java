/**
 * 
 */
package org.tedros.services.server.base.bo;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.tedros.services.server.base.eao.TServEAO;

import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.entity.ITEntity;

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

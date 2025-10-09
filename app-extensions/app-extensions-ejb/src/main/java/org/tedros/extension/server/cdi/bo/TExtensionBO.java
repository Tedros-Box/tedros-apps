/**
 * 
 */
package org.tedros.extension.server.cdi.bo;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import org.tedros.extension.server.cdi.eao.TExtensionEAO;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.entity.ITEntity;

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

/**
 * 
 */
package org.tedros.it.tools.cdi.bo;

import org.tedros.it.tools.cdi.eao.ItSupportToolsEAO;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.entity.ITEntity;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

/**
 * The CDI business object 
 * 
 * @author Davis Dun
 *
 */
@Dependent
public class ItSupportToolsBO<E extends ITEntity> extends TGenericBO<E> {

	@Inject
	private ItSupportToolsEAO<E> eao;
	
	@Override
	public ITGenericEAO<E> getEao() {
		return eao;
	}

}

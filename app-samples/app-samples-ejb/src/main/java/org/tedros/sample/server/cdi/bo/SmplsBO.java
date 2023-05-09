/**
 * 
 */
package org.tedros.sample.server.cdi.bo;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.tedros.sample.server.cdi.eao.SmplsEAO;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.entity.ITEntity;

/**
 * The CDI business object 
 * 
 * @author Davis
 *
 */
@Dependent
public class SmplsBO<E extends ITEntity> extends TGenericBO<E> {

	@Inject
	private SmplsEAO<E> eao;
	
	@Override
	public ITGenericEAO<E> getEao() {
		return eao;
	}

}

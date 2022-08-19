/**
 * 
 */
package org.tedros.docs.server.base.bo;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.tedros.docs.server.base.eao.TDocsEAO;

import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.entity.ITEntity;

/**
 * @author Davis Gordon
 *
 */
@Dependent
public class TDocsBO<E extends ITEntity> extends TGenericBO<E> {

	@Inject @Any
	private TDocsEAO<E> eao;
	
	@Override
	public ITGenericEAO<E> getEao() {
		return eao;
	}

}

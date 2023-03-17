/**
 * 
 */
package org.tedros.stock.server.cdi.eao;

import javax.enterprise.context.Dependent;

import org.tedros.server.cdi.eao.TGenericEAO;
import org.tedros.server.entity.ITEntity;

/**
 * The generic entity access object.
 * 
 * This use a Dependent context. 
 * 
 * @author Davis Dun
 *
 */
@Dependent
public class STCKEAO<E extends ITEntity> extends TGenericEAO<E> {

}

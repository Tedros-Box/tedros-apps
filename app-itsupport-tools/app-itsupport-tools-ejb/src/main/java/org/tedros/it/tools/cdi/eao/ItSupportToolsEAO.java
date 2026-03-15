/**
 * 
 */
package org.tedros.it.tools.cdi.eao;

import org.tedros.server.cdi.eao.TGenericEAO;
import org.tedros.server.entity.ITEntity;

import jakarta.enterprise.context.Dependent;

/**
 * The generic entity access object.
 * 
 * This use a Dependent context. 
 * 
 * @author Davis Dun
 *
 */
@Dependent
public class ItSupportToolsEAO<E extends ITEntity> extends TGenericEAO<E> {

}

/**
 * 
 */
package org.tedros.extension.server.base.eao;

import javax.enterprise.context.Dependent;

import org.tedros.server.cdi.eao.TGenericEAO;
import org.tedros.server.entity.ITEntity;

/**
 * @author Davis Gordon
 *
 */
@Dependent
public class TExtensionEAO<E extends ITEntity> extends TGenericEAO<E> {

}

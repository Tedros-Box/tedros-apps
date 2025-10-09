/**
 * 
 */
package org.tedros.person.server.cdi.eao;

import jakarta.enterprise.context.Dependent;

import org.tedros.server.cdi.eao.TGenericEAO;
import org.tedros.server.entity.ITEntity;

/**
 * @author Davis Gordon
 *
 */
@Dependent
public class TPersonEAO<E extends ITEntity> extends TGenericEAO<E> {

}

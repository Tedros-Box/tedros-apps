/**
 * 
 */
package org.tedros.sample.server.cdi.eao;

import javax.enterprise.context.Dependent;

import org.tedros.server.cdi.eao.TGenericEAO;
import org.tedros.server.entity.ITEntity;

/**
 * The generic entity access object.
 * 
 * This use a Dependent context. 
 * 
 * @author Davis
 *
 */
@Dependent
public class SmplsEAO<E extends ITEntity> extends TGenericEAO<E> {

}

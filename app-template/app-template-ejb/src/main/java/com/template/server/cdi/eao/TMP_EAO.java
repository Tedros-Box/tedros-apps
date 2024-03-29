/**
 * 
 */
package com.template.server.cdi.eao;

import javax.enterprise.context.Dependent;

import org.tedros.server.cdi.eao.TGenericEAO;
import org.tedros.server.entity.ITEntity;

/**
 * The generic entity access object.
 * 
 * This use a Dependent context. 
 * 
 * @author myname
 *
 */
@Dependent
public class TMP_EAO<E extends ITEntity> extends TGenericEAO<E> {

}

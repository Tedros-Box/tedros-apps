/**
 * 
 */
package com.template.server.base.eao;

import javax.enterprise.context.Dependent;

import com.tedros.ejb.base.eao.TGenericEAO;
import com.tedros.ejb.base.entity.ITEntity;

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

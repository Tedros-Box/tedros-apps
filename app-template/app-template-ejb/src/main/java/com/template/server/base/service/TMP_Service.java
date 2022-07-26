/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.template.server.base.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.service.TEjbService;
import com.template.server.base.bo.TMP_BO;

/**
 * The transact service bean 
 *
 * @author myname
 *
 */
@LocalBean
@Stateless(name="TMP_Service")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TMP_Service<E extends ITEntity> extends TEjbService<E>  {
	
	@Inject
	private TMP_BO<E> bo;
	
	@Override
	public ITGenericBO<E> getBussinesObject() {
		return bo;
	}
	

}

/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.tedros.services.server.base.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.services.server.base.bo.TServBO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="TServService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TServService<E extends ITEntity> extends TEjbService<E>  {
	
	@Inject
	private TServBO<E> bo;
	
	@Override
	public ITGenericBO<E> getBussinesObject() {
		return bo;
	}
	

}

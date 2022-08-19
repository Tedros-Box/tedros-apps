/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.tedros.server.base.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.server.base.bo.TLocBO;

import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.ejb.service.TEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="TLocService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TLocService<E extends ITEntity> extends TEjbService<E>  {
	
	@Inject
	private TLocBO<E> bo;
	
	@Override
	public ITGenericBO<E> getBussinesObject() {
		return bo;
	}
	

}

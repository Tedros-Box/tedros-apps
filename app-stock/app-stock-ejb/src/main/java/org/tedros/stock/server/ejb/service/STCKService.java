/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.tedros.stock.server.ejb.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;
import org.tedros.server.entity.ITEntity;

import org.tedros.stock.server.cdi.bo.STCKBO;

/**
 * The transact service bean 
 *
 * @author Davis Dun
 *
 */
@LocalBean
@Stateless(name="STCKService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class STCKService<E extends ITEntity> extends TEjbService<E>  {
	
	@Inject
	private STCKBO<E> bo;
	
	@Override
	public ITGenericBO<E> getBussinesObject() {
		return bo;
	}
	

}

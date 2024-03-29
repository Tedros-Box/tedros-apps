/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.tedros.services.server.ejb.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;
import org.tedros.server.entity.ITEntity;
import org.tedros.services.server.cdi.bo.TServBO;

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

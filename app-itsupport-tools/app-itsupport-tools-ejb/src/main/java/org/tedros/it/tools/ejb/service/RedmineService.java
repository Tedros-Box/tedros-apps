/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.tedros.it.tools.ejb.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import org.tedros.it.tools.cdi.bo.RedmineBO;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;
import org.tedros.server.entity.ITEntity;

/**
 * The transact service bean 
 *
 * @author Davis Dun
 *
 */
@LocalBean
@Stateless(name="RedmineService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class RedmineService<E extends ITEntity> extends TEjbService<E>  {
	
	@Inject
	private RedmineBO<E> bo;
	
	@Override
	public ITGenericBO<E> getBussinesObject() {
		return bo;
	}
	

}

/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.tedros.redminetools.server.ejb.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import org.tedros.redminetools.server.cdi.bo.RDMN_BO;
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
@Stateless(name="RDMN_Service")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class RDMN_Service<E extends ITEntity> extends TEjbService<E>  {
	
	@Inject
	private RDMN_BO<E> bo;
	
	@Override
	public ITGenericBO<E> getBussinesObject() {
		return bo;
	}
	

}

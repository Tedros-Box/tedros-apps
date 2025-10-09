/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.template.server.ejb.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;
import org.tedros.server.entity.ITEntity;

import com.template.server.cdi.bo.TMP_BO;

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

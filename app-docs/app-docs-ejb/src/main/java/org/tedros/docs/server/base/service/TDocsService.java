/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.tedros.docs.server.base.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.tedros.docs.server.base.bo.TDocsBO;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;
import org.tedros.server.entity.ITEntity;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@LocalBean
@Stateless(name="TDocsService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TDocsService<E extends ITEntity> extends TEjbService<E>  {
	
	@Inject @Any
	private TDocsBO<E> bo;
	
	@Override
	public ITGenericBO<E> getBussinesObject() {
		return bo;
	}
	

}

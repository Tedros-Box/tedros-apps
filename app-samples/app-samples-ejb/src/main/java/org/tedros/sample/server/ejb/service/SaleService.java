/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.tedros.sample.server.ejb.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import org.tedros.sample.entity.Sale;
import org.tedros.sample.server.cdi.bo.SaleBO;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;
import org.tedros.server.security.TAccessToken;

/**
 * The transact service bean 
 *
 * @author Davis
 *
 */
@LocalBean
@Stateless(name="SaleService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SaleService extends TEjbService<Sale>  {
	
	@Inject
	private SaleBO bo;
	
	@Override
	public ITGenericBO<Sale> getBussinesObject() {
		return bo;
	}
	 
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public Sale save(TAccessToken token, Sale entidade) throws Exception {
		return bo.save(token, entidade);
	}

	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public void remove(TAccessToken token, Sale entidade) throws Exception {
		bo.remove(token, entidade);
	}
}

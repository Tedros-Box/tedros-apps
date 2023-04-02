/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.tedros.stock.server.ejb.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;
import org.tedros.stock.entity.StockEvent;
import org.tedros.stock.server.cdi.bo.StockEventBO;

/**
 * The transact service bean 
 *
 * @author Davis Dun
 *
 */
@LocalBean
@Stateless(name="StockEventService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class StockEventService extends TEjbService<StockEvent>  {
	
	@Inject
	private StockEventBO bo;
	
	@Override
	public ITGenericBO<StockEvent> getBussinesObject() {
		return bo;
	}
}

/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.tedros.stock.server.ejb.service;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;
import org.tedros.server.exception.TBusinessException;
import org.tedros.stock.entity.StockEvent;
import org.tedros.stock.entity.StockOut;
import org.tedros.stock.server.cdi.bo.StockEventBO;

/**
 * The transact service bean 
 *
 * @author Davis Dun
 *
 */
@Singleton
@Lock(LockType.READ) 
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class StockEventService extends TEjbService<StockEvent>  {
	
	@Inject
	private StockEventBO bo;
	
	@Override
	public ITGenericBO<StockEvent> getBussinesObject() {
		return bo;
	}
	
	@Lock(LockType.WRITE) 
	public String validate(StockOut ev) throws TBusinessException{
		return bo.validate(ev);
	}

	/* (non-Javadoc)
	 * @see org.tedros.server.ejb.service.TEjbService#save(org.tedros.server.entity.ITEntity)
	 */
	@Override
	@Lock(LockType.WRITE) 
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public StockEvent save(StockEvent ev) throws Exception {
		return bo.save(ev);
	}

	/* (non-Javadoc)
	 * @see org.tedros.server.ejb.service.TEjbService#remove(org.tedros.server.entity.ITEntity)
	 */
	@Override
	@Lock(LockType.WRITE) 
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public void remove(StockEvent ev) throws Exception {
		bo.remove(ev);
	}
}

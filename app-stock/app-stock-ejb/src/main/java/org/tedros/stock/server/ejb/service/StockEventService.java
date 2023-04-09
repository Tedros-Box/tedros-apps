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
/*@LocalBean
@Stateless(name="StockEventService")*/

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
	@Lock(LockType.WRITE) 
	@Override
	public StockEvent save(StockEvent entidade) throws Exception {
		// TODO Auto-generated method stub
		return super.save(entidade);
	}

	/* (non-Javadoc)
	 * @see org.tedros.server.ejb.service.TEjbService#remove(org.tedros.server.entity.ITEntity)
	 */
	@Lock(LockType.WRITE) 
	@Override
	public void remove(StockEvent entidade) throws Exception {
		// TODO Auto-generated method stub
		super.remove(entidade);
	}
}

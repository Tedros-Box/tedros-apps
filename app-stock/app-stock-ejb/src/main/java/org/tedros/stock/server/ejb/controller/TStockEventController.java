/**
 * 
 */
package org.tedros.stock.server.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang.StringUtils;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;
import org.tedros.stock.domain.DomainApp;
import org.tedros.stock.ejb.controller.IStockEventController;
import org.tedros.stock.entity.StockEvent;
import org.tedros.stock.entity.StockOut;
import org.tedros.stock.server.ejb.service.StockEventService;

/**
 * The controller bean
 * 
 * @author Davis Dun
 *
 */
@TSecurityInterceptor
@Stateless(name="IStockEventController")
@TBeanSecurity({
	@TBeanPolicie(id = DomainApp.STOCK_ENTRY_FORM_ID, 
	policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS }),
	@TBeanPolicie(id = DomainApp.STOCK_OUT_FORM_ID, 
	policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })
	})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TStockEventController extends TSecureEjbController<StockEvent> implements IStockEventController, ITSecurity  {

	@EJB
	private StockEventService serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<StockEvent> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	/* (non-Javadoc)
	 * @see org.tedros.server.ejb.controller.TSecureEjbController#save(org.tedros.server.security.TAccessToken, org.tedros.server.entity.ITEntity)
	 */
	@Override
	public TResult<StockEvent> save(TAccessToken token, StockEvent ev) {
		String warn = null;
		if(ev instanceof StockOut) {
			StockOut out = (StockOut) ev;
			try {
				warn = serv.validate(out);
			}catch(Exception ex) {
				return processException(token, ev, ex);
			}
		}
		try{
			StockEvent e = getService().save(ev);
			processEntity(token, e);
			return StringUtils.isNotBlank(warn) 
					? new TResult<>(TState.SUCCESS, true, warn, e) 
							: new TResult<>(TState.SUCCESS, e);
		}catch(Exception e){
			return processException(token, ev, e);
		}
	}
	
	
}

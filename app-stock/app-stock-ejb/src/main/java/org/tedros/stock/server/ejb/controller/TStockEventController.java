/**
 * 
 */
package org.tedros.stock.server.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;
import org.tedros.stock.domain.DomainApp;
import org.tedros.stock.ejb.controller.IStockEventController;
import org.tedros.stock.entity.StockEvent;
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
}

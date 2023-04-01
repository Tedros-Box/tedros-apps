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
import org.tedros.stock.ejb.controller.IStockConfigController;
import org.tedros.stock.entity.StockConfig;
import org.tedros.stock.server.ejb.service.STCKService;

/**
 * The controller bean
 * 
 * @author Davis Dun
 *
 */
@TSecurityInterceptor
@Stateless(name="IStockConfigController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.STOCK_CONFIG_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TStockConfigController extends TSecureEjbController<StockConfig> implements IStockConfigController, ITSecurity  {

	@EJB
	private STCKService<StockConfig> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<StockConfig> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}

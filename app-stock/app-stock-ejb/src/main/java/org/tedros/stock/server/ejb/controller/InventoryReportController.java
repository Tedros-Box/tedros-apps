package org.tedros.stock.server.ejb.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TActionPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TMethodPolicie;
import org.tedros.server.security.TMethodSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.stock.domain.DomainApp;
import org.tedros.stock.ejb.controller.IInventoryReportController;
import org.tedros.stock.model.Inventory;
import org.tedros.stock.model.InventoryReportModel;
import org.tedros.stock.server.ejb.service.InventoryService;

@TSecurityInterceptor

@TBeanSecurity({@TBeanPolicie(id = DomainApp.INVENTORY_REPORT_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@Stateless(name="IInventoryReportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class InventoryReportController implements IInventoryReportController, ITSecurity {

	@EJB
	private InventoryService serv;
	
	@EJB
	private ITSecurityController security;
	
	public InventoryReportController() {
	}

	@Override	
	@TMethodSecurity({
		@TMethodPolicie(policie = {TActionPolicie.SEARCH})})
	public TResult<InventoryReportModel> process(TAccessToken token, InventoryReportModel m) {
		try{
			List<Inventory> lst = serv.calculate(m.getCostCenter(), m.getDate(), m.getProduct(), 
					m.getOrderBy(), m.getOrderType());
			if(lst!=null){
				m.setResult(lst);
			}
			return new TResult<>(TState.SUCCESS, m);
		}catch(Exception e){
			return new TResult<>(TState.ERROR, e.getMessage());
		}
	}

	@Override
	public ITSecurityController getSecurityController() {
		return security;
	}

}

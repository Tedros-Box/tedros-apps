package org.tedros.stock.server.ejb.support;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.person.model.CostCenter;
import org.tedros.person.model.LegalPerson;
import org.tedros.server.ejb.controller.ITSecurityController;
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
import org.tedros.stock.ejb.support.IInventorySupport;
import org.tedros.stock.entity.Product;
import org.tedros.stock.entity.StockEvent;
import org.tedros.stock.model.Inventory;
import org.tedros.stock.server.ejb.service.InventoryService;
import org.tedros.stock.server.ejb.service.StockEventService;

/**
 * Session Bean implementation class InventorySupport
 */
@TSecurityInterceptor
@Stateless(name="IInventorySupport")
@TBeanSecurity({
	@TBeanPolicie(id = DomainApp.STOCK_ENTRY_FORM_ID, 
	policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS }),
	@TBeanPolicie(id = DomainApp.STOCK_OUT_FORM_ID, 
	policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })
	})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class InventorySupport implements IInventorySupport, ITSecurity {

	@EJB
	private InventoryService invServ;
	
	@EJB
	private StockEventService stckServ; 

	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	@TMethodSecurity({@TMethodPolicie(policie = {TActionPolicie.SAVE, TActionPolicie.NEW})})
    public void addEvent(TAccessToken token, StockEvent e){
        try {
			stckServ.save(e);
		} catch (Exception e1) {
			String msg = this.processException(e1);
			throw new RuntimeException(msg);
		}
    }

	@TMethodSecurity({
		@TMethodPolicie(policie = {TActionPolicie.EDIT, TActionPolicie.READ, TActionPolicie.SEARCH})})
	public List<Inventory> calculate(TAccessToken token, LegalPerson lp, CostCenter cc, Date date, Product product){
		return invServ.calculate(lp, cc, date, product, null, null);
	}
	
	private String processException(Exception e) {
		e.printStackTrace();
		if(e instanceof EJBTransactionRolledbackException || e instanceof EJBException) {
			return e.getCause().getMessage();
		}else{
			return e.getMessage();
		}
	}
}

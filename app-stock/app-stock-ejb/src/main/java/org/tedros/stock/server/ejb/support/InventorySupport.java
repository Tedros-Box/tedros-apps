package org.tedros.stock.server.ejb.support;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.collections.ListUtils;
import org.tedros.person.model.CostCenter;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.model.Person;
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
import org.tedros.stock.ejb.support.TItem;
import org.tedros.stock.ejb.support.TItem.TEvent;
import org.tedros.stock.entity.Product;
import org.tedros.stock.entity.StockEntry;
import org.tedros.stock.entity.StockItem;
import org.tedros.stock.entity.StockOut;
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
    public String addEvent(TAccessToken token, LegalPerson lp, CostCenter cc, Person resp, Date date, 
			List<TItem> items, List<TItem> oldItems){
        try {
        	
        	StockOut out = new StockOut();
			out.setDate(new Date());
			out.setLegalPerson(lp);
			out.setCostCenter(cc);
			out.setResponsable(resp);
			out.setItems(new ArrayList<>());
			
        	StockEntry in = new StockEntry();
			in.setDate(new Date());
			in.setLegalPerson(lp);
			in.setCostCenter(cc);
			in.setResponsable(resp);
			in.setItems(new ArrayList<>());
			
        	List<TItem> res = analyse(items, oldItems);
        	res.forEach(i->{
				StockItem si = new StockItem();
				si.setProduct(i.getProduct());
				si.setAmount(i.getAmount());
        		if(i.getEvent().equals(TEvent.OUT)) {
        			si.setEvent(out);
        			out.getItems().add(si);
        		}else {
        			si.setEvent(in);
        			in.getItems().add(si);
        		}
        	});
        	
        	String msg = null;
        	if(!out.getItems().isEmpty()) {
        		msg = stckServ.validate(out);
        		stckServ.save(out);
        	}
        	if(!in.getItems().isEmpty()) {
        		stckServ.save(in);
        	}
        	return msg;
		} catch (Exception e1) {
			String msg = this.processException(e1);
			throw new RuntimeException(msg);
		}
    }

	@SuppressWarnings("unchecked")
	private List<TItem> analyse(List<TItem> items, List<TItem> oldItems) {
		List<TItem> res = new ArrayList<>();
		if(oldItems!=null && !oldItems.isEmpty()) {
			List<TItem> rem = ListUtils.removeAll(oldItems, items);
			List<TItem> add = ListUtils.removeAll(items, oldItems);
			List<TItem> ret = new ArrayList<>();
			rem.forEach(i->i.setEvent(TEvent.ENTRY));
			add.forEach(i->i.setEvent(TEvent.OUT));
			oldItems.forEach(o->{
				items.stream()
				.filter(p->p.getId()!=null && p.getId().equals(o.getId()) 
						&& !p.getAmount().equals(o.getAmount()))
				.findFirst()
				.ifPresent(c->{
					if(o.getAmount()>c.getAmount())
						ret.add(new TItem(o.getId(), o.getProduct(), 
								o.getAmount()-c.getAmount(), TEvent.ENTRY));
					else
						ret.add(new TItem(o.getId(), o.getProduct(), 
								c.getAmount()-o.getAmount(), TEvent.OUT));
				});
			});
			res.addAll(rem);
			res.addAll(add);
			res.addAll(ret);
		}else
			res = items;
		return res;
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

/**
 * 
 */
package org.tedros.sample.server.cdi.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.sample.entity.Sale;
import org.tedros.sample.server.cdi.eao.SmplsEAO;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.security.TAccessToken;
import org.tedros.stock.ejb.support.IInventorySupport;
import org.tedros.stock.ejb.support.TItem;
import org.tedros.stock.ejb.support.TItem.TEvent;

/**
 * The CDI business object 
 * 
 * @author Davis
 *
 */
@RequestScoped
public class SaleBO extends TGenericBO<Sale> {

	@Inject
	private SmplsEAO<Sale> eao;
	
	@EJB
	private IInventorySupport support;
	
	
	@Override
	public ITGenericEAO<Sale> getEao() {
		return eao;
	}

	public Sale save(TAccessToken token, Sale e) throws Exception {
		
		List<TItem> oldItems = new ArrayList<>();
		List<TItem> currItems = new ArrayList<>();
		if(e.isNew()) {
			e.getItems().forEach(i->{
				currItems.add(new TItem(i.getProduct(), new Double(i.getAmount())));
			});
		}else {
			Sale old = super.findById(e);
			old.getItems().forEach(i->{
				oldItems.add(new TItem(i.getId(), i.getProduct(), new Double(i.getAmount())));
			});
			e.getItems().forEach(i->{
				currItems.add(new TItem(i.getId(), i.getProduct(), new Double(i.getAmount())));
			});
		}
		try {
			support.addEvent(token, e.getLegalPerson(), e.getCostCenter(), e.getSeller(), new Date(), currItems, oldItems);
			return super.save(e);
		}catch(Exception ex) {
			throw ex;
		}
	}
	
	public void remove(TAccessToken token, Sale e) throws Exception {
		List<TItem> currItems = new ArrayList<>();
		Sale current = super.findById(e);
		current.getItems().forEach(i->{
			TItem item = new TItem(i.getId(), i.getProduct(), new Double(i.getAmount()));
			item.setEvent(TEvent.ENTRY);
			currItems.add(item);
		});
		try {
			support.addEvent(token, e.getLegalPerson(), e.getCostCenter(), e.getSeller(), new Date(), currItems, null);
			super.remove(e);
		}catch(Exception ex) {
			throw ex;
		}
	}
	
	
	
	
	
}

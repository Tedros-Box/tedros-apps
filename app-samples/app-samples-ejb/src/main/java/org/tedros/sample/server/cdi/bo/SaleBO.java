/**
 * 
 */
package org.tedros.sample.server.cdi.bo;

import java.util.ArrayList;
import java.util.Date;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.sample.entity.Sale;
import org.tedros.sample.server.cdi.eao.SmplsEAO;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.security.TAccessToken;
import org.tedros.stock.ejb.support.IInventorySupport;
import org.tedros.stock.entity.StockEntry;
import org.tedros.stock.entity.StockItem;
import org.tedros.stock.entity.StockOut;

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
	private IInventorySupport sup;
	
	
	@Override
	public ITGenericEAO<Sale> getEao() {
		return eao;
	}

	public Sale save(TAccessToken token, Sale e) throws Exception {
		
		
		if(e.isNew()) {
			StockOut out = new StockOut();
			out.setDate(new Date());
			out.setLegalPerson(e.getLegalPerson());
			out.setCostCenter(e.getCostCenter());
			out.setResponsable(e.getSeller());
			out.setItems(new ArrayList<>());
			e.getItems().forEach(si->{
				StockItem i = new StockItem();
				i.setProduct(si.getProduct());
				i.setAmount(new Double(si.getAmount()));
				i.setEvent(out);
				out.getItems().add(i);
			});
			sup.addEvent(token, out);
		}
		
		return super.save(e);
	}
	
	
	
	
	
	
	
}

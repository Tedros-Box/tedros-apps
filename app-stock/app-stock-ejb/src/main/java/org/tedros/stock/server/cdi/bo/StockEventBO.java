/**
 * 
 */
package org.tedros.stock.server.cdi.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.exception.TBusinessException;
import org.tedros.stock.entity.Product;
import org.tedros.stock.entity.StockConfig;
import org.tedros.stock.entity.StockConfigItem;
import org.tedros.stock.entity.StockEvent;
import org.tedros.stock.entity.StockItem;
import org.tedros.stock.entity.StockOut;
import org.tedros.stock.model.Inventory;
import org.tedros.stock.server.cdi.eao.InventoryEAO;
import org.tedros.stock.server.cdi.eao.StockEventEAO;

/**
 * The CDI business object 
 * 
 * @author Davis Dun
 *
 */
@RequestScoped
public class StockEventBO extends TGenericBO<StockEvent> {

	@Inject
	private StockEventEAO eao;
	
	@Inject
	private InventoryEAO invEao;

	@Inject
	private STCKBO<StockConfig> cfgBo;
	
	@Override
	public ITGenericEAO<StockEvent> getEao() {
		return eao;
	}
	
	public String validate(StockOut ev) throws TBusinessException{
		StockConfig cfg = null;
		try {
			StockConfig ex = new StockConfig();
			ex.setCostCenter(ev.getCostCenter());
			cfg = cfgBo.find(ex);
		}catch(Exception ex) {}
		
		// get a list of config items from output products
		List<StockConfigItem> citems = cfg != null 
				? cfg.getItems().stream()
				.filter(p->{
					return ev.getItems().stream()
							.anyMatch(x -> x.getProduct().equals(p.getProduct()));
				}).collect(Collectors.toList())
					: new ArrayList<>();
				
		// get the inventory list from output products
		List<Product> prods = new ArrayList<>();
		ev.getItems().forEach(p->prods.add(p.getProduct()));
		List<Inventory> iLst = invEao.calculate(ev.getLegalPerson(), ev.getCostCenter(), null, prods, null, null);
		
		StringBuilder sbWarn = new StringBuilder("");
		StringBuilder sbExc = new StringBuilder("");
		
		// validate 
		ev.getItems().stream().forEach(si->{
			
			Optional<StockConfigItem> cOp = citems.stream()
			.filter(x->x.getProduct().equals(si.getProduct()))
			.findFirst();
			StockConfigItem i = cOp.isPresent() ? cOp.get() : null;
			
			Boolean allowNeg = i!=null ? i.getAllowNegativeStock() : false;
			Optional<Inventory> op = iLst.stream().filter(p->{
				return p.getProdId().equals(si.getProduct().getId());
			}).findFirst();
			
			Double current = 0D;
			if(op.isPresent()) {
				current = op.get().getAmount();
				if(!si.isNew()) {
					try {
						StockItem svItem = invEao.findById(si);
						current += svItem.getAmount();
					} catch (Exception e) {}
				}
			}
			Double output = current - si.getAmount();
					
			if(i!=null && i.getMinimumAmount()>=output)
				sbWarn.append(si.getProduct().toString()+" #{reach.minimum.amount}\n");
			
			if((allowNeg==null || !allowNeg) && output<0)
				sbExc.append(si.getProduct().toString()+" #{with.insufficient.stock}\n");
		});
		
		if(sbExc.length()>0)
			throw new TBusinessException(sbExc.toString());
		
		return sbWarn.toString();
		
	}
}

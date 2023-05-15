/**
 * 
 */
package org.tedros.stock.server.cdi.bo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.person.model.CostCenter;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.stock.entity.Product;
import org.tedros.stock.entity.StockConfig;
import org.tedros.stock.entity.StockItem;
import org.tedros.stock.model.Inventory;
import org.tedros.stock.server.cdi.eao.InventoryEAO;

/**
 * The CDI business object 
 * 
 * @author Davis Dun
 *
 */
@RequestScoped
public class InventoryBO extends TGenericBO<StockItem> {

	@Inject
	private InventoryEAO eao;
	
	@Inject
	private STCKBO<StockConfig> cfgBo;
	
	@Override
	public ITGenericEAO<StockItem> getEao() {
		return eao;
	}
	
	public List<Inventory> calculate(CostCenter cc, Date date, Product product, String orderBy, String asc){
		
		List<Inventory> l = eao.calculate(cc, date, product!=null?Arrays.asList(product):null, orderBy, asc);	
		
		StockConfig cfg = new StockConfig();
		cfg.setCostCenter(cc);
		try {
			cfg = cfgBo.find(cfg);
			if(cfg!=null) {
				cfg.getItems().forEach(i->{
					Optional<Inventory> op = l.stream().filter(p->{
						return p.getProdId().equals(i.getProduct().getId());
					}).findFirst();
					if(op.isPresent())
						op.get().setMinAmount(i.getMinimumAmount());
				});
			}
		} catch (Exception e) {
		}
		
		return l;
	}

}

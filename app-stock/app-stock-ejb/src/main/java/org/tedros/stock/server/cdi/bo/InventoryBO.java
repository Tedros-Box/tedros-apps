/**
 * 
 */
package org.tedros.stock.server.cdi.bo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.stock.entity.CostCenter;
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
	
	@Override
	public ITGenericEAO<StockItem> getEao() {
		return eao;
	}
	
	public List<Inventory> calculate(CostCenter cc){
		return eao.calculate(cc);	
	}

}

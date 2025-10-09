/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.tedros.stock.server.ejb.service;

import java.util.Date;
import java.util.List;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import org.tedros.person.model.CostCenter;
import org.tedros.person.model.LegalPerson;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;
import org.tedros.stock.entity.Product;
import org.tedros.stock.entity.StockItem;
import org.tedros.stock.model.Inventory;
import org.tedros.stock.server.cdi.bo.InventoryBO;

/**
 * The transact service bean 
 *
 * @author Davis Dun
 *
 */
@LocalBean
@Stateless(name="InventoryService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class InventoryService extends TEjbService<StockItem>  {
	
	@Inject
	private InventoryBO bo;
	
	@Override
	public ITGenericBO<StockItem> getBussinesObject() {
		return bo;
	}

	public List<Inventory> calculate(LegalPerson lp, CostCenter cc, Date date, Product product, String orderBy, String asc){
		return bo.calculate(lp, cc, date, product, orderBy, asc);
	}
}

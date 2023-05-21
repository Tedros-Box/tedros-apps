/**
 * 
 */
package org.tedros.stock.ejb.support;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import org.tedros.person.model.CostCenter;
import org.tedros.person.model.LegalPerson;
import org.tedros.server.security.TAccessToken;
import org.tedros.stock.entity.Product;
import org.tedros.stock.entity.StockEvent;
import org.tedros.stock.model.Inventory;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IInventorySupport {

	void addEvent(TAccessToken token, StockEvent e);
	
	List<Inventory> calculate(TAccessToken token, LegalPerson lp, CostCenter cc, Date date, Product product);	
}

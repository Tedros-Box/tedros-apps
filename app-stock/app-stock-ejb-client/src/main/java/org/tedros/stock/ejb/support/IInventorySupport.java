/**
 * 
 */
package org.tedros.stock.ejb.support;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import org.tedros.person.model.CostCenter;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.model.Person;
import org.tedros.server.security.TAccessToken;
import org.tedros.stock.entity.EntryType;
import org.tedros.stock.entity.OutType;
import org.tedros.stock.entity.Product;
import org.tedros.stock.model.Inventory;

/**
 * 
 * Support bean for updating product inventory
 * 
 * @author Davis Gordon
 *
 */
@Remote
public interface IInventorySupport {

	/**
	 * Updates the stock. The items parameter must contain the products to be
	 * updated in stock. and the oldItems parameter must be used in case of changing
	 * the items list.
	 *
	 * @param token       - login user access token
	 * @param legalPerson - the company whose stock will be changed
	 * @param costCenter  - the cost center of the inventory
	 * @param resp        - the person responsible for the action
	 * @param date        - the date and time of the operation
	 * @param entryType   - the entry type to use for stock entry events
	 * @param outType     - the output type to use for stock out events
	 * @param items       - the items to be analyzed for stock update
	 * @param oldItems    - the old items to be updated, if any.
	 * @return Warning message, if any.
	 * @throws EjbException in case of business exception
	 */
	String updateStock(TAccessToken token, LegalPerson legalPerson, CostCenter costCenter, Person resp, Date date,
			EntryType entryType, OutType outType, List<TItem> items, List<TItem> oldItems);

	/**
	 * Calculates and returns the stock position according to the informed
	 * parameters
	 *
	 * @param token       - login user access token
	 * @param legalPerson - the company whose stock will be changed
	 * @param costCenter  - the cost center of the inventory
	 * @param date        - the calculation will be done until this date, if null
	 *                    the entire stock will be calculated
	 * @param product     - the desired product, if null all products will be
	 *                    included
	 * @return a list of {@link Inventory}
	 */
	List<Inventory> calculate(TAccessToken token, LegalPerson legalPerson, CostCenter costCenter, Date date,
			Product product);
}

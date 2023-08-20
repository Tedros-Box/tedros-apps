/**
 * 
 */
package org.tedros.sample.server.cdi.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.BooleanUtils;
import org.tedros.person.model.CostCenter;
import org.tedros.person.model.Employee;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.model.Person;
import org.tedros.sample.entity.Order;
import org.tedros.sample.entity.OrderStatus;
import org.tedros.sample.entity.SaleEventConfig;
import org.tedros.sample.server.cdi.eao.OrderEao;
import org.tedros.sample.server.cdi.eao.SmplsEAO;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.security.TAccessToken;
import org.tedros.stock.ejb.support.IInventorySupport;
import org.tedros.stock.ejb.support.TItem;

/**
 * Order business object.
 *
 * In this example we create a unique BO (business object) to handle how the
 * Order entity should be saved or deleted.
 *
 * Here we need to update product stock when a sale in the order is carried out, changed or
 * deleted and for that we have to integrate our solution with the Stock app
 * injecting the support bean responsible for this operation.
 * 
 * @author Davis
 */
@RequestScoped
public class OrderBO extends TGenericBO<Order> {

	/**
	 * The Order entity access object
	 */
	@Inject
	private OrderEao eao;

	/**
	 * Entity access object to retrieve settings
	 */
	@Inject
	private SmplsEAO<SaleEventConfig> secEao;

	/**
	 * The inventory support bean from Stock app
	 */
	@EJB
	private IInventorySupport support;

	@Override
	public ITGenericEAO<Order> getEao() {
		return eao;
	}

	public Map<Date, Long> count(Date begin, Date end,
			LegalPerson legalPerson, CostCenter costCenter,
			Person customer, Employee seller, OrderStatus status){
		return eao.count(begin, end, legalPerson, costCenter, customer, seller, status);
	}
	
	/**
	 * The custom save operation to handle stock updating
	 * @param token
	 * @param e
	 * @return
	 * @throws Exception
	 */
	public Order save(TAccessToken token, Order e) throws Exception {
		// getting the sale event settings
		SaleEventConfig config = null;
		List<SaleEventConfig> list = secEao.listAll(SaleEventConfig.class);
		if (list != null && !list.isEmpty())
			config = list.get(0);
		
		// validate if stock update is enabled
		if (config != null && BooleanUtils.isTrue(config.getUpdateStock())) {
			List<TItem> oldItems = new ArrayList<>();  // the last items list 
			List<TItem> currItems = new ArrayList<>(); // the current items list
			
			if (e.getSale()!=null && e.getSale().isNew()) { 
				// the entity is new and the persist operation will be performed
				// we just need the current items
				e.getSale().getItems().forEach(i -> {
					currItems.add(new TItem(i.getProduct(), new Double(i.getAmount())));
				});
			}
			try {
				// calls the inventory support bean, in case of problems an EjbException will be thrown
				support.updateStock(token, e.getLegalPerson(), e.getCostCenter(), e.getSeller(), new Date(),
						config.getEntryType(), config.getOutType(), currItems, oldItems);
				// save the sale if the stock update is successful
				return super.save(e);
			} catch (Exception ex) {
				throw ex;
			}
		} else // save the sale without stock update
			return super.save(e);
	}
}

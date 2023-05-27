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

import org.apache.commons.lang3.BooleanUtils;
import org.tedros.sample.entity.Sale;
import org.tedros.sample.entity.SaleEventConfig;
import org.tedros.sample.server.cdi.eao.SmplsEAO;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.server.security.TAccessToken;
import org.tedros.stock.ejb.support.IInventorySupport;
import org.tedros.stock.ejb.support.TItem;
import org.tedros.stock.ejb.support.TItem.TEvent;

/**
 * Sale business object.
 *
 * In this example we create a unique BO (business object) to handle how the
 * Sale entity should be saved or deleted.
 *
 * Here we need to update product stock when a sale is carried out, changed or
 * deleted and for that we have to integrate our solution with the Stock app
 * injecting the support bean responsible for this operation.
 * 
 * @author Davis
 */
@RequestScoped
public class SaleBO extends TGenericBO<Sale> {

	/**
	 * The Sale entity access object
	 */
	@Inject
	private SmplsEAO<Sale> eao;

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
	public ITGenericEAO<Sale> getEao() {
		return eao;
	}

	/**
	 * The custom save operation to handle stock updating
	 * @param token
	 * @param e
	 * @return
	 * @throws Exception
	 */
	public Sale save(TAccessToken token, Sale e) throws Exception {
		// getting the sale event settings
		SaleEventConfig config = null;
		List<SaleEventConfig> list = secEao.listAll(SaleEventConfig.class);
		if (list != null && !list.isEmpty())
			config = list.get(0);
		
		// validate if stock update is enabled
		if (config != null && BooleanUtils.isTrue(config.getUpdateStock())) {
			List<TItem> oldItems = new ArrayList<>();  // the last items list 
			List<TItem> currItems = new ArrayList<>(); // the current items list
			
			if (e.isNew()) { 
				// the entity is new and the persist operation will be performed
				// we just need the current items
				e.getItems().forEach(i -> {
					currItems.add(new TItem(i.getProduct(), new Double(i.getAmount())));
				});
			} else {
				// the entity is not new and the merge operation will be performed
				// we need current state and last state of items
				// to be parsed by the inventory support bean
				Sale old = super.findById(e); // get the last state of the entity
				old.getItems().forEach(i -> {
					oldItems.add(new TItem(i.getId(), i.getProduct(), new Double(i.getAmount())));
				});
				e.getItems().forEach(i -> {
					currItems.add(new TItem(i.getId(), i.getProduct(), new Double(i.getAmount())));
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

	public void remove(TAccessToken token, Sale e) throws Exception {
		SaleEventConfig config = null;
		List<SaleEventConfig> list = secEao.listAll(SaleEventConfig.class);
		if (list != null && !list.isEmpty())
			config = list.get(0);

		if (config != null && BooleanUtils.isTrue(config.getUpdateStock())) {
			List<TItem> currItems = new ArrayList<>();
			Sale current = super.findById(e);
			current.getItems().forEach(i -> {
				TItem item = new TItem(i.getId(), i.getProduct(), new Double(i.getAmount()));
				item.setEvent(TEvent.ENTRY);
				currItems.add(item);
			});
			try {
				support.updateStock(token, e.getLegalPerson(), e.getCostCenter(), e.getSeller(), new Date(),
						config.getEntryType(), config.getOutType(), currItems, null);
				super.remove(e);
			} catch (Exception ex) {
				throw ex;
			}
		} else
			super.remove(e);
	}

}

/**
 * 
 */
package org.tedros.stock.server.cdi.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.stock.entity.StockEvent;
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
	
	@Override
	public ITGenericEAO<StockEvent> getEao() {
		return eao;
	}

}

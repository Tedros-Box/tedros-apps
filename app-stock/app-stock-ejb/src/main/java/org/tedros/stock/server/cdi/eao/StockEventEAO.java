/**
 * 
 */
package org.tedros.stock.server.cdi.eao;

import javax.enterprise.context.RequestScoped;

import org.tedros.server.cdi.eao.TGenericEAO;
import org.tedros.stock.entity.StockEvent;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class StockEventEAO extends TGenericEAO<StockEvent> {

	@Override
	public void beforeMerge(StockEvent e) throws Exception {
		setEvent(e);
	}

	@Override
	public void beforePersist(StockEvent e) throws Exception {
		setEvent(e);
	}
	
	private void setEvent(StockEvent e) {
		if(e.getItems()!=null)
			e.getItems().forEach(c->{
				c.setEvent(e);
			});
	}
}

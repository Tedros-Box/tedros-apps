/**
 * 
 */
package org.tedros.samples.module.sale.trigger;

import java.util.List;

import org.tedros.fx.control.TBigDecimalField;
import org.tedros.fx.control.trigger.TTrigger;
import org.tedros.fx.form.TFieldBox;
import org.tedros.fx.process.TEntityProcess;
import org.tedros.sample.ejb.controller.IProductPriceController;
import org.tedros.sample.entity.ProductPrice;
import org.tedros.server.result.TResult;
import org.tedros.stock.entity.Product;

import javafx.concurrent.Worker.State;

/**
 * @author Davis Gordon
 *
 */
public class SetPriceTrigger extends TTrigger<Product> {

	public SetPriceTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
	}

	@Override
	public void run(Product value) {
		ProductPrice ex = new ProductPrice();
		ex.setProduct(value);
		TEntityProcess<ProductPrice> prc = 
		new TEntityProcess<ProductPrice>(ProductPrice.class, IProductPriceController.JNDI_NAME) {};
		prc.stateProperty().addListener((a,o,n)->{
			if(n.equals(State.SUCCEEDED)) {
				List<TResult<ProductPrice>> l = prc.getValue();
				if(!l.isEmpty()) {
					TResult<ProductPrice> r = l.get(0);
					if(r.getValue()!=null) {
						TBigDecimalField c = (TBigDecimalField) super.getTarget().gettControl();
						c.setValue(r.getValue().getUnitPrice());
					}
				}
			}
		});
		prc.find(ex);
		prc.startProcess();
	}

}

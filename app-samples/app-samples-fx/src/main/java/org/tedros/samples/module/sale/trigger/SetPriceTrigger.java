/**
 * 
 */
package org.tedros.samples.module.sale.trigger;

import java.util.List;

import org.tedros.api.presenter.ITPresenter;
import org.tedros.core.message.TMessage;
import org.tedros.core.message.TMessageType;
import org.tedros.fx.control.TBigDecimalField;
import org.tedros.fx.control.trigger.TTrigger;
import org.tedros.fx.form.TFieldBox;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.entity.behavior.TDetailFieldBehavior;
import org.tedros.fx.presenter.view.group.TGroupPresenter;
import org.tedros.fx.process.TEntityProcess;
import org.tedros.person.model.CostCenter;
import org.tedros.person.model.LegalPerson;
import org.tedros.sample.ejb.controller.IProductPriceController;
import org.tedros.sample.entity.ProductPrice;
import org.tedros.sample.entity.SaleItem;
import org.tedros.samples.SmplsKey;
import org.tedros.samples.module.sale.model.SaleItemMV;
import org.tedros.samples.module.sale.model.SaleMV;
import org.tedros.server.result.TResult;
import org.tedros.stock.entity.Product;

import javafx.concurrent.Worker.State;

/**
 * This trigger will be fired to fetch 
 * the price of a product when it is selected
 * 
 * @author Davis Gordon
 *
 */
public class SetPriceTrigger extends TTrigger<Product> {

	public SetPriceTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void run(TEvent event, Product value, Product old) {
		/*
		 * The product price are related to a cost center.
		 * We need to get the LegalPerson and the CostCenter 
		 * in the main entity and with the Product search the 
		 * ProductPrice entity.
		 * 
		 * To do this:
		 * */
		
		// Get the presenter from form.
		TDynaPresenter<SaleItemMV> detailPresenter =  (TDynaPresenter<SaleItemMV>) super.getForm().gettPresenter();
		// Get the behavior from the given presenter, in this case TDetailFieldBehavior because the 
		// SaleItemMV are annotated with TDetailTableViewPresenter which use it.
		TDetailFieldBehavior<SaleItemMV, SaleItem> detailBehavior =  
				(TDetailFieldBehavior<SaleItemMV, SaleItem>) detailPresenter.getBehavior();
		// From this behavior we can get the main presenter
		// As the SaleModule use the TGroupView the returned presenter 
		// will be a TGroupPresenter.
		ITPresenter pre = detailBehavior.getModulePresenter();
		TDynaPresenter<SaleMV> masterPresenter = (TDynaPresenter) 
				((TGroupPresenter)pre).getSelectedView().gettPresenter();
		// With the main presenter we can get the SaleMV
		SaleMV mv = masterPresenter.getModelView();
		// Getting the LegalPerson and CostCenter from SaleMV
		LegalPerson lp = mv.getLegalPerson().getValue();
		CostCenter cc = mv.getCostCenter().getValue();
		
		if(lp==null || cc==null) {
			//Show a info message
			detailBehavior.addMessage(new TMessage(TMessageType.INFO, SmplsKey.MSG_SELECT_COST_CENTER));
		}else {
			// Search the ProductPrice
			ProductPrice ex = new ProductPrice();
			ex.setProduct(value);
			ex.setLegalPerson(lp);
			ex.setCostCenter(cc);
			// Create and start an entity process
			TEntityProcess<ProductPrice> prc = 
			new TEntityProcess<ProductPrice>(ProductPrice.class, IProductPriceController.JNDI_NAME) {};
			prc.stateProperty().addListener((a,o,n)->{
				if(n.equals(State.SUCCEEDED)) {
					List<TResult<ProductPrice>> l = prc.getValue();
					if(!l.isEmpty()) {
						TResult<ProductPrice> r = l.get(0);
						if(r.getValue()!=null) {
							// Setting the price
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

}

/**
 * 
 */
package org.tedros.samples.module.sale.action;

import java.util.List;

import javax.naming.NamingException;

import org.tedros.core.context.TedrosContext;
import org.tedros.core.service.remote.ServiceLocator;
import org.tedros.fx.control.action.TPresenterAction;
import org.tedros.fx.presenter.behavior.TActionType;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.sample.ejb.controller.ISaleEventConfigController;
import org.tedros.sample.entity.SaleEventConfig;
import org.tedros.samples.module.sale.model.SaleEventConfigMV;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;

/**
 * @author Davis Gordon
 *
 */
public class NewSaleEventConfigAction extends TPresenterAction {

	/**
	 * @param tActionType
	 */
	public NewSaleEventConfigAction() {
		super(TActionType.NEW);
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.control.action.TPresenterAction#runBefore()
	 */
	@Override
	public boolean runBefore() {
		
		ServiceLocator loc = ServiceLocator.getInstance();
		try {
			ISaleEventConfigController serv = loc.lookup(ISaleEventConfigController.JNDI_NAME);
			TResult<List<SaleEventConfig>> res = 
					serv.listAll(TedrosContext.getLoggedUser().getAccessToken(), SaleEventConfig.class);
			if(res.getState().equals(TState.SUCCESS) && !res.getValue().isEmpty()) {
				SaleEventConfig entity = res.getValue().get(0);
				TDynaPresenter<SaleEventConfigMV> p = super.getPresenter();
				p.getBehavior().setModelView(new SaleEventConfigMV(entity));
				return false;
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}finally {
			loc.close();
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.control.action.TPresenterAction#runAfter()
	 */
	@Override
	public void runAfter() {
		// TODO Auto-generated method stub

	}

}

/**
 * 
 */
package org.tedros.samples.module.order;

import org.tedros.core.TModule;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.presenter.dynamic.view.TDynaGroupView;
import org.tedros.fx.presenter.view.group.TGroupPresenter;
import org.tedros.fx.presenter.view.group.TGroupView;
import org.tedros.fx.presenter.view.group.TViewItem;
import org.tedros.sample.domain.DomainApp;
import org.tedros.samples.SmplsKey;
import org.tedros.samples.module.order.model.OrderMV;
import org.tedros.samples.module.order.model.OrderStatusMV;

/**
 * @author Davis
 *
 */
@TSecurity(id=DomainApp.ORDER_MODULE_ID, 
appName = SmplsKey.APP_SAMPLES, 
moduleName = SmplsKey.MODULE_ORDERS, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class OrderModule extends TModule {

	/* (non-Javadoc)
	 * @see org.tedros.samples.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TGroupView<TGroupPresenter>(this, SmplsKey.MODULE_ORDERS, 
				new TViewItem(TDynaGroupView.class, OrderMV.class, SmplsKey.VIEW_ORDERS),
				new TViewItem(TDynaGroupView.class, OrderStatusMV.class, SmplsKey.VIEW_ORDER_STATUS)
				));

	}

}

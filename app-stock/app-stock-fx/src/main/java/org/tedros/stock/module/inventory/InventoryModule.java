/**
 * 
 */
package org.tedros.stock.module.inventory;

import org.tedros.core.TModule;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.presenter.dynamic.view.TDynaGroupView;
import org.tedros.fx.presenter.view.group.TGroupPresenter;
import org.tedros.fx.presenter.view.group.TGroupView;
import org.tedros.fx.presenter.view.group.TViewItem;
import org.tedros.stock.STCKKey;
import org.tedros.stock.domain.DomainApp;
import org.tedros.stock.module.inventory.model.ConfigMV;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(id=DomainApp.STOCK_CONFIG_MODULE_ID, 
appName = STCKKey.APP_STOCK, 
moduleName = STCKKey.MODULE_INVENTORY, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class InventoryModule extends TModule {

	/* (non-Javadoc)
	 * @see org.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TGroupView<TGroupPresenter>(this, STCKKey.MODULE_INVENTORY, 
				new TViewItem(TDynaGroupView.class, ConfigMV.class, STCKKey.VIEW_STOCK_CONFIG)
				));
	}

}

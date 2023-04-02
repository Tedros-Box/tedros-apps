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
import org.tedros.stock.module.inventory.model.EntryTypeMV;
import org.tedros.stock.module.inventory.model.OutTypeMV;
import org.tedros.stock.module.inventory.model.StockEntryMV;
import org.tedros.stock.module.inventory.model.StockOutMV;
import org.tedros.stock.module.report.model.InventoryReportMV;

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
				new TViewItem(TDynaGroupView.class, ConfigMV.class, STCKKey.VIEW_STOCK_CONFIG), 
				new TViewItem(TDynaGroupView.class, StockEntryMV.class, STCKKey.VIEW_STOCK_ENTRY), 
				new TViewItem(TDynaGroupView.class, EntryTypeMV.class, STCKKey.VIEW_ENTRY_TYPE), 
				new TViewItem(TDynaGroupView.class, StockOutMV.class, STCKKey.VIEW_STOCK_OUT), 
				new TViewItem(TDynaGroupView.class, OutTypeMV.class, STCKKey.VIEW_OUT_TYPE), 
				new TViewItem(TDynaGroupView.class, InventoryReportMV.class, STCKKey.VIEW_INVENTORY_REPORT)
				));
	}

}

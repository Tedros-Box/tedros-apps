/**
 * 
 */
package org.tedros.stock.module.inventory;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.stock.STCKKey;
import org.tedros.stock.domain.DomainApp;
import org.tedros.stock.entity.EntryType;
import org.tedros.stock.entity.OutType;
import org.tedros.stock.entity.StockConfig;
import org.tedros.stock.entity.StockEntry;
import org.tedros.stock.entity.StockOut;
import org.tedros.stock.model.InventoryReportModel;
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
@TView(title=STCKKey.MODULE_INVENTORY,
items = { 
	@TItem(title = STCKKey.VIEW_STOCK_CONFIG, description=STCKKey.VIEW_STOCK_CONFIG_DESC,
	model=StockConfig.class, modelView=ConfigMV.class),
	@TItem(title = STCKKey.VIEW_STOCK_ENTRY, description=STCKKey.VIEW_STOCK_ENTRY_DESC,
	model=StockEntry.class, modelView=StockEntryMV.class),
	@TItem(title = STCKKey.VIEW_ENTRY_TYPE, description=STCKKey.VIEW_ENTRY_TYPE_DESC,
	model=EntryType.class, modelView=EntryTypeMV.class),
	@TItem(title = STCKKey.VIEW_STOCK_OUT, description=STCKKey.VIEW_STOCK_OUT_DESC,
	model=StockOut.class, modelView=StockOutMV.class),
	@TItem(title = STCKKey.VIEW_OUT_TYPE, description=STCKKey.VIEW_OUT_TYPE_DESC,
	model=OutType.class, modelView=OutTypeMV.class),
	@TItem(title = STCKKey.VIEW_INVENTORY_REPORT, description=STCKKey.VIEW_INVENTORY_REPORT_DESC,
	model=InventoryReportModel.class, modelView=InventoryReportMV.class),
})
@TSecurity(id=DomainApp.STOCK_CONFIG_MODULE_ID, 
appName = STCKKey.APP_STOCK, 
moduleName = STCKKey.MODULE_INVENTORY, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class InventoryModule extends TModule {


}

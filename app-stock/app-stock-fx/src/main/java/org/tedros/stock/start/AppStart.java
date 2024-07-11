package org.tedros.stock.start;

import org.tedros.core.ITApplication;
import org.tedros.core.annotation.TApplication;
import org.tedros.core.annotation.TModule;
import org.tedros.core.annotation.TResourceBundle;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.stock.STCKKey;
import org.tedros.stock.domain.DomainApp;
import org.tedros.stock.module.inventory.InventoryModule;
import org.tedros.stock.module.products.ProductModule;
import org.tedros.stock.resource.AppResource;

/**
 * The app start class.
 * 
 * @author Davis Dun
 * */
@TApplication(name=STCKKey.APP_STOCK, 
module = {	
	@TModule(type=ProductModule.class, 
			name=STCKKey.MODULE_PRODUCTS, 
			menu=STCKKey.MENU_STOCK, 
			description=STCKKey.MODULE_DESC_PRODUCTS,
			icon=TConstant.ICONS_FOLDER + "product.png", 
			menuIcon=TConstant.ICONS_FOLDER + "product_menu.png"),
	@TModule(type=InventoryModule.class, 
		name=STCKKey.MODULE_INVENTORY, 
		menu=STCKKey.MENU_STOCK, 
		description=STCKKey.MODULE_INVENTORY_DESC,
		icon=TConstant.ICONS_FOLDER + "inventory.png", 
		menuIcon=TConstant.ICONS_FOLDER + "inventory_menu.png")
}, packageName = "org.tedros.stock", universalUniqueIdentifier=TConstant.UUI)
@TResourceBundle(resourceName={"STCK"})
@TSecurity(id=DomainApp.MNEMONIC, 
appName = STCKKey.APP_STOCK, 
allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		AppResource.createResource();
	}

	@Override
	public void stop() {
		// Executed on exit and logout
	}


}

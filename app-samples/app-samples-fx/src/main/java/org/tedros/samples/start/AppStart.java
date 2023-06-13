package org.tedros.samples.start;

import org.tedros.core.ITApplication;
import org.tedros.core.annotation.TApplication;
import org.tedros.core.annotation.TModule;
import org.tedros.core.annotation.TResourceBundle;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.sample.domain.DomainApp;
import org.tedros.samples.SmplsKey;
import org.tedros.samples.module.forms.AdvancedControlModule;
import org.tedros.samples.module.forms.BasicControlModule;
import org.tedros.samples.module.forms.FileControlModule;
import org.tedros.samples.module.order.OrderModule;
import org.tedros.samples.module.price.PriceModule;
import org.tedros.samples.module.sale.SaleModule;

/**
 * The app start class.
 * 
 * @author Davis
 * */
@TApplication(name=SmplsKey.APP_SAMPLES, 
	module = {	
		@TModule(type=SaleModule.class, 
		name=SmplsKey.MODULE_SALES, 
		menu=SmplsKey.MENU_CRUD_VIEWS, 
		description=SmplsKey.MODULE_DESC_SALES),
		@TModule(type=OrderModule.class, 
		name=SmplsKey.MODULE_ORDERS, 
		menu=SmplsKey.MENU_CRUD_VIEWS, 
		description=SmplsKey.MODULE_DESC_ORDERS),
		@TModule(type=PriceModule.class, 
		name=SmplsKey.MODULE_PRICE, 
		menu=SmplsKey.MENU_CRUD_VIEWS, 
		description=SmplsKey.MODULE_DESC_PRICE),
		@TModule(type=BasicControlModule.class, 
		name="Basic Controls", 
		menu="Samples", 
		description="Basic field controls"),
		@TModule(type=AdvancedControlModule.class, 
		name="Advanced Controls", 
		menu="Samples", 
		description="Advanced field controls"),
		@TModule(type=FileControlModule.class, 
		name="File Controls", 
		menu="Samples", 
		description="File field controls")
	}, packageName = "org.tedros.samples", 
	universalUniqueIdentifier=TConstant.UUI)
@TResourceBundle(resourceName={"Smpls"})
@TSecurity(id=DomainApp.MNEMONIC, 
	appName = SmplsKey.APP_SAMPLES, 
	allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		// Run at startup
	}

	@Override
	public void stop() {
		// Executed on exit and logout
	}
	
	
}

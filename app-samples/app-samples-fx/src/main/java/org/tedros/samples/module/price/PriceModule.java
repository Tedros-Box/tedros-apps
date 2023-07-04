/**
 * 
 */
package org.tedros.samples.module.price;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.sample.domain.DomainApp;
import org.tedros.sample.entity.ProductPrice;
import org.tedros.samples.SmplsKey;
import org.tedros.samples.module.price.model.PriceMV;

/**
 * @author Davis Gordon
 *
 */
@TView(items = { 
	@TItem(title = SmplsKey.VIEW_PRICE, 
	model=ProductPrice.class, modelView=PriceMV.class) })
@TSecurity(id=DomainApp.PRODUCT_PRICE_MODULE_ID, 
	appName = SmplsKey.APP_SAMPLES, 
	moduleName = SmplsKey.MODULE_PRICE, 
	allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class PriceModule extends TModule {
	
}

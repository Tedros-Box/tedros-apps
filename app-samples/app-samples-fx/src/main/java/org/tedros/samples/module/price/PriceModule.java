/**
 * 
 */
package org.tedros.samples.module.price;

import org.tedros.core.TModule;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.sample.domain.DomainApp;
import org.tedros.samples.SmplsKey;
import org.tedros.samples.module.price.model.PriceMV;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(id=DomainApp.PRODUCT_PRICE_MODULE_ID, 
appName = SmplsKey.APP_SAMPLES, 
moduleName = SmplsKey.MODULE_PRICE, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class PriceModule extends TModule {
	
	/**
	 * (non-Javadoc)
	 * @see org.tedros.samples.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TDynaView<PriceMV>(this, PriceMV.class));
	}

}

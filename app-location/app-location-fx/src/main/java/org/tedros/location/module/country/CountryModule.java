/**
 * 
 */
package org.tedros.location.module.country;

import org.tedros.core.TModule;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.location.LocatKey;
import org.tedros.location.domain.DomainApp;
import org.tedros.location.module.country.model.CountryMV;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id=DomainApp.COUNTRY_MODULE_ID, 
appName = LocatKey.APP_LOCATION_NAME, 
moduleName = LocatKey.MODULE_ADMINISTRATIVE, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class CountryModule extends TModule {

	/* (non-Javadoc)
	 * @see org.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TDynaView<>(CountryMV.class));

	}

}

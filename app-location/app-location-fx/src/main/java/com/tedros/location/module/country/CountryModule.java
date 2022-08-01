/**
 * 
 */
package com.tedros.location.module.country;

import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.location.LocatKey;
import com.tedros.location.domain.DomainApp;
import com.tedros.location.module.country.model.CountryMV;

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
	 * @see com.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TDynaView<>(CountryMV.class));

	}

}

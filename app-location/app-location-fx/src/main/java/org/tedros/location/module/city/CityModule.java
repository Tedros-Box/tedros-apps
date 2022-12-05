/**
 * 
 */
package org.tedros.location.module.city;

import org.tedros.core.TModule;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.location.LocatKey;
import org.tedros.location.domain.DomainApp;
import org.tedros.location.module.city.model.CityMV;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id=DomainApp.CITY_MODULE_ID, 
appName = LocatKey.APP_LOCATION_NAME, 
moduleName = LocatKey.MODULE_ADMINISTRATIVE, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class CityModule extends TModule {

	/* (non-Javadoc)
	 * @see org.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TDynaView<>(CityMV.class));

	}

}

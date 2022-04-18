/**
 * 
 */
package com.tedros.location.module.place;

import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.location.domain.DomainApp;
import com.tedros.location.module.place.model.PlaceMV;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id=DomainApp.PLACE_MODULE_ID, appName = "#{app.location.name}", 
moduleName = "#{module.administrative}", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class PlaceModule extends TModule {

	/* (non-Javadoc)
	 * @see com.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TDynaView<>(PlaceMV.class));

	}

}

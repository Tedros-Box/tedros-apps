/**
 * 
 */
package com.tedros.location.module.adminArea;

import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.location.domain.DomainApp;
import com.tedros.location.module.adminArea.model.AdminAreaMV;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id=DomainApp.ADMIN_AREA_MODULE_ID, appName = "#{app.location.name}", 
moduleName = "#{module.administrative}", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class AdminAreaModule extends TModule {

	/* (non-Javadoc)
	 * @see com.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TDynaView<>(AdminAreaMV.class));

	}

}

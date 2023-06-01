/**
 * 
 */
package org.tedros.extension.module.adminArea;

import org.tedros.core.TModule;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.extension.LocatKey;
import org.tedros.extension.domain.DomainApp;
import org.tedros.extension.module.adminArea.model.AdminAreaMV;
import org.tedros.fx.presenter.dynamic.view.TDynaView;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id=DomainApp.ADMIN_AREA_MODULE_ID, 
appName = LocatKey.APP_LOCATION_NAME, 
moduleName = LocatKey.MODULE_ADMINISTRATIVE, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class AdminAreaModule extends TModule {

	/* (non-Javadoc)
	 * @see org.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TDynaView<>(AdminAreaMV.class));

	}

}

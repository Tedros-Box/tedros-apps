/**
 * 
 */
package com.tedros.services.module.service;

import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaGroupView;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;
import com.tedros.services.ServKey;
import com.tedros.services.domain.DomainApp;
import com.tedros.services.module.service.model.ServiceLocationMV;
import com.tedros.services.module.service.model.ServiceTypeMV;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(id=DomainApp.SERVICE_MODULE_ID, 
appName = ServKey.APP_SERVICE, 
moduleName = ServKey.MODULE_SERVICES, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class ServiceModule extends TModule {

	/* (non-Javadoc)
	 * @see com.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TGroupView<TGroupPresenter>(this, ServKey.VIEW_SERVICE, 
				new TViewItem(TDynaGroupView.class, ServiceTypeMV.class, ServKey.VIEW_SERVICE_TYPE),
				new TViewItem(TDynaGroupView.class, ServiceLocationMV.class, ServKey.VIEW_SERVICE_LOCATION)/*, 
				new TViewItem(TDynaGroupView.class, StaffTypeMV.class, PersonKeys.VIEW_STAFF_TYPE), 
				new TViewItem(TDynaGroupView.class, EmployeeMV.class, PersonKeys.VIEW_EMPLOYEES)*/
				));

	}

}

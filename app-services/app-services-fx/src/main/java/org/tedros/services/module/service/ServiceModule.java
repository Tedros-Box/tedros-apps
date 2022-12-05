/**
 * 
 */
package org.tedros.services.module.service;

import org.tedros.core.TModule;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.presenter.dynamic.view.TDynaGroupView;
import org.tedros.fx.presenter.view.group.TGroupPresenter;
import org.tedros.fx.presenter.view.group.TGroupView;
import org.tedros.fx.presenter.view.group.TViewItem;
import org.tedros.services.ServKey;
import org.tedros.services.domain.DomainApp;
import org.tedros.services.module.service.model.ContractMV;
import org.tedros.services.module.service.model.ServiceLocationMV;
import org.tedros.services.module.service.model.ServiceMV;
import org.tedros.services.module.service.model.ServiceTypeMV;

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
	 * @see org.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TGroupView<TGroupPresenter>(this, ServKey.VIEW_SERVICE, 
				new TViewItem(TDynaGroupView.class, ServiceTypeMV.class, ServKey.VIEW_SERVICE_TYPE),
				new TViewItem(TDynaGroupView.class, ServiceLocationMV.class, ServKey.VIEW_SERVICE_LOCATION), 
				new TViewItem(TDynaGroupView.class, ServiceMV.class, ServKey.VIEW_SERVICE), 
				new TViewItem(TDynaGroupView.class, ContractMV.class, ServKey.VIEW_CONTRACT)
				));

	}

}

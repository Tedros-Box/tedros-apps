/**
 * 
 */
package com.tedros.services.module.plan;

import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.services.ServKey;
import com.tedros.services.domain.DomainApp;
import com.tedros.services.module.plan.model.PlanMV;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(id=DomainApp.PLAN_MODULE_ID, 
appName = ServKey.APP_SERVICE, 
moduleName = ServKey.MODULE_PLANS, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class PlanModule extends TModule {

	/* (non-Javadoc)
	 * @see com.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TDynaView<PlanMV>(this, PlanMV.class));

	}

}

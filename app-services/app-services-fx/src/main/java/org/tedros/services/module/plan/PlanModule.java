/**
 * 
 */
package org.tedros.services.module.plan;

import org.tedros.services.ServKey;
import org.tedros.services.domain.DomainApp;
import org.tedros.services.module.plan.model.PlanMV;

import org.tedros.core.TModule;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.presenter.dynamic.view.TDynaView;

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
	 * @see org.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TDynaView<PlanMV>(this, PlanMV.class));

	}

}

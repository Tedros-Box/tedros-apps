/**
 * 
 */
package org.tedros.services.module.plan;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.services.ServKey;
import org.tedros.services.domain.DomainApp;
import org.tedros.services.model.Plan;
import org.tedros.services.module.plan.model.PlanMV;

/**
 * @author Davis Gordon
 *
 */
@TView(items = { @TItem(title = ServKey.VIEW_PLAN, 
	model=Plan.class, modelView=PlanMV.class, 
	description=ServKey.VIEW_PLAN_DESC) })
@TSecurity(id=DomainApp.PLAN_MODULE_ID, 
appName = ServKey.APP_SERVICE, 
moduleName = ServKey.MODULE_PLANS, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class PlanModule extends TModule {

}

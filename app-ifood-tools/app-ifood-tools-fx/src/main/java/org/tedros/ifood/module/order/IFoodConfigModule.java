/**
 * 
 */
package org.tedros.ifood.module.order;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.ifood.IFOODKey;
import org.tedros.ifood.domain.DomainApp;
import org.tedros.ifood.module.order.model.OrderDashboardMV;
import org.tedros.ifood.module.order.model.OrderDashboardModel;

/**
 * @author Davis Dun
 *
 */
@TView(items = { 
	@TItem(title = IFOODKey.MY_APP_MY_VIEW, 
			modelView=OrderDashboardMV.class, model=OrderDashboardModel.class)})
/*@TSecurity(id=DomainApp.IFOOD_CONFIG__MODULE_ID, 
appName = IFOODKey.APP_MY_APP, 
moduleName = IFOODKey.MODULE_MY_APP, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)*/
public class IFoodConfigModule extends TModule {

}

/**
 * 
 */
package org.tedros.stock.module.costcenter;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TLoadable;
import org.tedros.core.annotation.TModel;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.stock.STCKKey;
import org.tedros.stock.domain.DomainApp;
import org.tedros.stock.entity.CostCenter;
import org.tedros.stock.module.costcenter.model.CostCenterMV;

/**
 * @author Davis Gordon
 *
 */@TSecurity(id=DomainApp.COST_CENTER_MODULE_ID, 
		 appName = STCKKey.APP_STOCK, 
		 moduleName = STCKKey.MODULE_COST_CENTER, 
		 allowedAccesses=TAuthorizationType.MODULE_ACCESS)
		 @TLoadable({
		 	@TModel(modelType = CostCenter.class, 
		 			modelViewType=CostCenterMV.class,
		 			moduleType=CostCenterModule.class)
		 })
public class CostCenterModule extends TModule {

	/* (non-Javadoc)
	 * @see org.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TDynaView<>(this, CostCenterMV.class));
	}


}

/**
 * 
 */
package org.tedros.samples.module.sale;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TLoadable;
import org.tedros.core.annotation.TModel;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.presenter.dynamic.view.TDynaGroupView;
import org.tedros.fx.presenter.view.group.TGroupPresenter;
import org.tedros.fx.presenter.view.group.TGroupView;
import org.tedros.fx.presenter.view.group.TViewItem;
import org.tedros.sample.domain.DomainApp;
import org.tedros.sample.entity.Sale;
import org.tedros.samples.SmplsKey;
import org.tedros.samples.module.sale.model.SaleEventConfigMV;
import org.tedros.samples.module.sale.model.SaleMV;
import org.tedros.samples.module.sale.model.SaleStatusMV;
import org.tedros.samples.module.sale.model.SaleTypeMV;

/**
 * @author Davis
 *
 */
@TLoadable(value = { 
		@TModel(modelType = Sale.class, 
				modelViewType = SaleMV.class, 
				moduleType = SaleModule.class)})
@TSecurity(id=DomainApp.SALE_MODULE_ID, 
appName = SmplsKey.APP_SAMPLES, 
moduleName = SmplsKey.MODULE_SALES, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class SaleModule extends TModule {

	/* (non-Javadoc)
	 * @see org.tedros.samples.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TGroupView<TGroupPresenter>(this, SmplsKey.MODULE_SALES, 
				new TViewItem(TDynaGroupView.class, SaleMV.class, SmplsKey.VIEW_SALES),
				new TViewItem(TDynaGroupView.class, SaleTypeMV.class, SmplsKey.VIEW_SALES_TYPE),
				new TViewItem(TDynaGroupView.class, SaleStatusMV.class, SmplsKey.VIEW_SALES_STATUS),
				new TViewItem(TDynaGroupView.class, SaleEventConfigMV.class, SmplsKey.VIEW_SALES_EVENT_CONFIG)
				));

	}

}

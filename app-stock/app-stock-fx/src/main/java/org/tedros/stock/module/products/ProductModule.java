/**
 * 
 */
package org.tedros.stock.module.products;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TLoadable;
import org.tedros.core.annotation.TModel;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.presenter.dynamic.view.TDynaGroupView;
import org.tedros.fx.presenter.view.group.TGroupPresenter;
import org.tedros.fx.presenter.view.group.TGroupView;
import org.tedros.fx.presenter.view.group.TViewItem;
import org.tedros.stock.STCKKey;
import org.tedros.stock.domain.DomainApp;
import org.tedros.stock.entity.Product;
import org.tedros.stock.module.products.model.ProductMV;
import org.tedros.stock.module.report.model.ProductReportMV;

/**
 * @author Davis Dun
 *
 */
@TSecurity(id=DomainApp.PRODUCT_MODULE_ID, 
appName = STCKKey.APP_STOCK, 
moduleName = STCKKey.MODULE_PRODUCTS, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
@TLoadable({
	@TModel(modelType = Product.class, 
			modelViewType=ProductMV.class,
			moduleType=ProductModule.class)
})
public class ProductModule extends TModule {

	/* (non-Javadoc)
	 * @see org.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TGroupView<TGroupPresenter>(this, STCKKey.MODULE_PRODUCTS, 
				new TViewItem(TDynaGroupView.class, ProductMV.class, STCKKey.VIEW_PRODUCT), 
				new TViewItem(TDynaGroupView.class, ProductReportMV.class, STCKKey.VIEW_PRODUCT_REPORT)
				));
	}

}

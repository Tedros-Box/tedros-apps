/**
 * 
 */
package org.tedros.stock.module.products;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.stock.STCKKey;
import org.tedros.stock.domain.DomainApp;
import org.tedros.stock.entity.Product;
import org.tedros.stock.model.ProductReportModel;
import org.tedros.stock.module.products.model.ProductMV;
import org.tedros.stock.module.report.model.ProductReportMV;

/**
 * @author Davis Dun
 *
 */
@TView(title=STCKKey.MODULE_PRODUCTS,
items = {
	@TItem(title = STCKKey.VIEW_PRODUCT, description=STCKKey.VIEW_PRODUCT_DESC,
	model = Product.class, modelView=ProductMV.class),
	@TItem(title = STCKKey.VIEW_PRODUCT_REPORT, description=STCKKey.VIEW_PRODUCT_REPORT_DESC,
	model = ProductReportModel.class, modelView=ProductReportMV.class)
})
@TSecurity(id=DomainApp.PRODUCT_MODULE_ID, 
appName = STCKKey.APP_STOCK, 
moduleName = STCKKey.MODULE_PRODUCTS, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class ProductModule extends TModule {

}

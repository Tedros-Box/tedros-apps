/**
 * 
 */
package org.tedros.stock.server.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;
import org.tedros.stock.domain.DomainApp;
import org.tedros.stock.ejb.controller.IProductController;
import org.tedros.stock.ejb.controller.IProductReportController;
import org.tedros.stock.entity.Product;
import org.tedros.stock.model.ProductReportModel;
import org.tedros.stock.server.ejb.service.ProductService;

/**
 * The controller bean
 * 
 * @author Davis Dun
 *
 */
@TSecurityInterceptor
@Stateless(name="IProductController")
@TBeanSecurity({
	@TBeanPolicie(id = DomainApp.PRODUCT_FORM_ID, 
	policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS }),
	@TBeanPolicie(id = DomainApp.PRODUCT_REPORT_FORM_ID, 
	policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })
})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TProductController extends TSecureEjbController<Product> 
implements IProductController, IProductReportController, ITSecurity  {

	@EJB
	private ProductService serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<Product> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	@Override
	public TResult<ProductReportModel> process(TAccessToken token, ProductReportModel model) {
		try{
			serv.process(model);
			
			return new TResult<>(TState.SUCCESS, model);
		}catch(Exception e){
			return new TResult<>(TState.ERROR, e.getMessage());
		}
	}
}

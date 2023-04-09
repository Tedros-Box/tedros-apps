/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.tedros.stock.server.ejb.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;
import org.tedros.stock.entity.Product;
import org.tedros.stock.model.ProductReportModel;
import org.tedros.stock.server.cdi.bo.ProductBO;

/**
 * The transact service bean 
 *
 * @author Davis Dun
 *
 */
@LocalBean
@Stateless(name="ProductService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ProductService extends TEjbService<Product>  {
	
	@Inject
	private ProductBO bo;
	
	@Override
	public ITGenericBO<Product> getBussinesObject() {
		return bo;
	}

	public void process(ProductReportModel model) {
		bo.process(model);
	}
}

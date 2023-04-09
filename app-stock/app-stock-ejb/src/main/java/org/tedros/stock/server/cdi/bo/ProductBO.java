/**
 * 
 */
package org.tedros.stock.server.cdi.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;
import org.tedros.stock.entity.Product;
import org.tedros.stock.model.ProductItem;
import org.tedros.stock.model.ProductReportModel;
import org.tedros.stock.server.cdi.eao.ProductEAO;

/**
 * The CDI business object 
 * 
 * @author Davis Dun
 *
 */
@RequestScoped
public class ProductBO extends TGenericBO<Product> {

	@Inject
	private ProductEAO eao;
	
	@Override
	public ITGenericEAO<Product> getEao() {
		return eao;
	}
	
	public void process(ProductReportModel model) {
		List<String> codes = null;
		if(model.getCodes()!=null) {
			String[] arr = model.getCodes().split(",");
			codes = Arrays.asList(arr);
		}
		List<Product> l = eao.search(model.getName(), model.getTrademark(), codes, 
				model.getOrderBy(), model.getOrderType());
		List<ProductItem> items = new ArrayList<>();
		l.forEach(p->{
			items.add(new ProductItem(p));
		});
		model.setResult(items);
	}
}

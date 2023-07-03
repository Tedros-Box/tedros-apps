/**
 * 
 */
package org.tedros.samples.ai.function;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Empty;
import org.tedros.ai.function.model.Response;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.service.remote.ServiceLocator;
import org.tedros.sample.ejb.controller.IProductPriceController;
import org.tedros.sample.entity.ProductPrice;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;

/**
 * This function provides data on product prices 
 * to artificial intelligence
 * 
 * @author Davis Gordon
 *
 */
public class ListProductPriceAiFunction extends TFunction<Empty> {

	public ListProductPriceAiFunction() {
		super("list_products_price", "Lists all products price", Empty.class, 
				v->{
					ServiceLocator loc = ServiceLocator.getInstance();
					try {
						IProductPriceController serv = loc.lookup(IProductPriceController.JNDI_NAME);
						TResult<List<ProductPrice>> res = serv
								.listAll(TedrosContext.getLoggedUser().getAccessToken(), ProductPrice.class);
						if(res.getState().equals(TState.SUCCESS) && !res.getValue().isEmpty()) {
							List<Price> lst = new ArrayList<>();
							res.getValue().forEach(p->{
								lst.add(new Price(p));
							});
							return new Response("The products price list", lst);
						}
					} catch (NamingException e) {
						e.printStackTrace();
					}finally {
						loc.close();
					}
					
					return new Response("No data found");
				});
	}

}

/**
 * 
 */
package org.tedros.samples.ai.function;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Empty;
import org.tedros.ai.function.model.Response;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.sample.ejb.controller.IProductPriceController;
import org.tedros.sample.entity.ProductPrice;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.util.TLoggerUtil;

/**
 * This function provides data on product prices 
 * to artificial intelligence
 * 
 * @author Davis Gordon
 *
 */
public class ListProductPriceAiFunction extends TFunction<Empty> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(ListProductPriceAiFunction.class);
	
	public static final String NAME = "list_products_price";
	public static final String DESCRIPTION = "Lists all products prices";

	public ListProductPriceAiFunction() {
		super(NAME, DESCRIPTION, Empty.class, 
			v->{
				
				LOGGER.info("Listing all products prices");
								
				try(TEjbServiceLocator loc = TEjbServiceLocator.getInstance()) {
					IProductPriceController serv = loc.lookup(IProductPriceController.JNDI_NAME);
					TResult<List<ProductPrice>> res = serv
						.listAll(TedrosContext.getLoggedUser().getAccessToken(), ProductPrice.class);
					
					if(res.getState().equals(TState.SUCCESS) && !res.getValue().isEmpty()) {
						List<Price> lst = new ArrayList<>();
						res.getValue().forEach(p-> lst.add(new Price(p)));
						return new Response(SUSCESS_MESSAGE, lst);
					}
				} catch (NamingException e) {
					LOGGER.error(e.getMessage(), e);
					return new Response(EXCEPTION_MESSAGE + e.getMessage());
				}
				
				return new Response(NO_DATA_FOUND_MESSAGE);
		});
	}
}

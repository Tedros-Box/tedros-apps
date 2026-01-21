/**
 * 
 */
package org.tedros.samples.ai.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Empty;
import org.tedros.ai.openai.model.ToolCallResult;
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
						return ToolCallResult.builder()
								.message("Product prices retrieved successfully.")
								.result(Map.of(
					                    STATUS, SUCCESS,
					                    ACTION, "product_prices_listed",
					                    SYSTEM_INSTRUCTION, "Product prices listed successfully. "
					                    		+ "Do not retry again. Proceed with the user's request.",
					                    "product_prices", lst
					                ))
								.build();
					}
				} catch (NamingException e) {
					LOGGER.error(e.getMessage(), e);
					return ToolCallResult.builder()
							.message("Error listing product prices: " + e.getMessage())
							.result(Map.of(
				                    STATUS, ERROR,
				                    ACTION, "product_price_list_error",
				                    ERROR_MESSAGE, e.getMessage()
				                ))
							.build();
				}
				
				return ToolCallResult.builder()
						.message("No product prices found.")
						.result(Map.of(
			                    STATUS, ERROR,
			                    ACTION, "no_product_prices_found",
			                    ERROR_MESSAGE, "No product prices available in the system."
			                ))
						.build();
		});
	}
}  
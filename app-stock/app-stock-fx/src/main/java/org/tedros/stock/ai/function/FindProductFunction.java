/**
 * 
 */
package org.tedros.stock.ai.function;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TSelect;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.stock.ejb.controller.IProductController;
import org.tedros.stock.entity.Product;
import org.tedros.util.TLoggerUtil;

/**
 * 
 */
public class FindProductFunction extends TFunction<ProductParam> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(FindProductFunction.class);
	
	public static final String NAME = "search_products";
	public static final String DESCRIPTION = "Searches the product catalog/inventory using partial matches. ";
	
	private static final String INSERT_DATE = "insertDate";

	public FindProductFunction() {
		super(NAME, DESCRIPTION, ProductParam.class, 
				v->{
					
					LOGGER.info("Searching Products with parameters: {}", v);
					
					TSelect<Product> sel = new TSelect<>(Product.class);

					if(StringUtils.isNotBlank(v.getCode())) 
						sel.addOrCondition("code", TCompareOp.LIKE, v.getCode().trim().toLowerCase());
					
					if(StringUtils.isNotBlank(v.getName())) 
						sel.addOrCondition("name", TCompareOp.LIKE, v.getName().trim().toLowerCase());
					
					if(StringUtils.isNotBlank(v.getDescription())) 
						sel.addOrCondition("description", TCompareOp.LIKE, v.getDescription().trim().toLowerCase());
					
					if(StringUtils.isNotBlank(v.getTrademark())) 
						sel.addOrCondition("trademark", TCompareOp.LIKE, v.getTrademark().trim().toLowerCase());
					
					if(StringUtils.isNotBlank(v.getUnitMeasure())) 
						sel.addOrCondition("unitMeasure", TCompareOp.LIKE, v.getUnitMeasure().trim().toLowerCase());
					
					if(v.getMeasure()!=null) 
						sel.addOrCondition("measure", TCompareOp.EQUAL, v.getMeasure());
					
					if(v.getBeginDate()!=null) {
						if(v.getBeginDate()!=null && v.getEndDate()==null)
							sel.addOrCondition(INSERT_DATE, TCompareOp.EQUAL, v.getBeginDate());
						else {
							sel.addOrCondition(INSERT_DATE, TCompareOp.GREATER_EQ_THAN, v.getBeginDate());
							sel.addAndCondition(INSERT_DATE, TCompareOp.LESS_THAN, v.getEndDate());
						}
					}else if(v.getEndDate()!=null)
						sel.addOrCondition(INSERT_DATE, TCompareOp.LESS_EQ_THAN, v.getEndDate());
					
					
					try(TEjbServiceLocator loc = TEjbServiceLocator.getInstance()) {
						IProductController serv = loc.lookup(IProductController.JNDI_NAME);
						TResult<List<Product>> res = serv.search(TedrosContext.getLoggedUser().getAccessToken(), sel);
						if(res.getState().equals(TState.SUCCESS)) {
							if(res.getValue().isEmpty())
								return new Response(NO_DATA_FOUND_MESSAGE);
							
							List<ProductParam> lst = new ArrayList<>();
							res.getValue().forEach(p->lst.add(new ProductParam(p)));
							
							return new Response(SUSCESS_MESSAGE, lst); 
						}
						
					} catch (NamingException e) {
						LOGGER.error(e.getMessage(), e);
						return new Response(EXCEPTION_MESSAGE + e.getMessage());
					}
					
					return new Response(FAILURE_MESSAGE);
				});

	}

}

/**
 * 
 */
package org.tedros.stock.ai.function;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.service.remote.ServiceLocator;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TSelect;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.stock.ejb.controller.IProductController;
import org.tedros.stock.entity.Product;

/**
 * 
 */
public class FindProductFunction extends TFunction<ProductParam> {

	public FindProductFunction() {
		super("search_products", "Search for products based on the fields provided, lists all if no field is filled in", ProductParam.class, 
				v->{
					
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
							sel.addOrCondition("insertDate", TCompareOp.EQUAL, v.getBeginDate());
						else {
							sel.addOrCondition("insertDate", TCompareOp.GREATER_EQ_THAN, v.getBeginDate());
							sel.addAndCondition("insertDate", TCompareOp.LESS_THAN, v.getEndDate());
						}
					}else if(v.getEndDate()!=null)
						sel.addOrCondition("insertDate", TCompareOp.LESS_EQ_THAN, v.getEndDate());
					
					ServiceLocator loc = ServiceLocator.getInstance();
					try {
						IProductController serv = loc.lookup(IProductController.JNDI_NAME);
						TResult<List<Product>> res = serv.search(TedrosContext.getLoggedUser().getAccessToken(), sel);
						if(res.getState().equals(TState.SUCCESS)) {
							if(res.getValue().isEmpty())
								return new Response("No data found!");
							
							List<ProductParam> lst = new ArrayList<>();
							res.getValue().forEach(p->lst.add(new ProductParam(p)));
							
							return new Response("Result list", lst); 
						}
						
					} catch (NamingException e) {
						e.printStackTrace();
						return new Response("An error occurred!");
					}finally {
						loc.close();
					}
					return new Response("The operation fail!");
				});

	}

}

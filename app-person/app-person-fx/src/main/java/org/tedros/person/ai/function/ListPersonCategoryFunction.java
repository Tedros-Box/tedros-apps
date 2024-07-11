/**
 * 
 */
package org.tedros.person.ai.function;

import java.util.List;

import javax.naming.NamingException;

import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Empty;
import org.tedros.ai.function.model.Response;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.service.remote.ServiceLocator;
import org.tedros.person.ejb.controller.IPersonCategoryController;
import org.tedros.person.model.PersonCategory;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;

/**
 * @author Davis Gordon
 *
 */
public class ListPersonCategoryFunction extends TFunction<Empty> {
	
	public ListPersonCategoryFunction() {
		super("list_categories_person", "List all categories that can be assigned to a person", Empty.class, 
				v->{
					
					ServiceLocator loc = ServiceLocator.getInstance();
					try {
						IPersonCategoryController serv = loc.lookup(IPersonCategoryController.JNDI_NAME);
						TResult<List<PersonCategory>> res = serv.listAll(TedrosContext.getLoggedUser().getAccessToken(), PersonCategory.class);
						if(res.getState().equals(TState.SUCCESS)) {
							if(res.getValue().isEmpty())
								return new Response("No data found!");
							return new Response("Result list", res.getValue()); 
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

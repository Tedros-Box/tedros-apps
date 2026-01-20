/**
 * 
 */
package org.tedros.person.ai.function;

import java.util.List;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Empty;
import org.tedros.ai.function.model.Response;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.person.ejb.controller.IPersonCategoryController;
import org.tedros.person.model.PersonCategory;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.util.TLoggerUtil;

/**
 * @author Davis Gordon
 *
 */
public class ListPersonCategoryFunction extends TFunction<Empty> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(ListPersonCategoryFunction.class);
	
	public static final String NAME = "list_categories_person";
	public static final String DESCRIPTION = "List all categories that can be assigned to a person";
	
	public ListPersonCategoryFunction() {
		super(NAME, DESCRIPTION, Empty.class, 
				v->{
					LOGGER.info("Listing all person categories");
					try(TEjbServiceLocator loc = TEjbServiceLocator.getInstance()) {
						IPersonCategoryController serv = loc.lookup(IPersonCategoryController.JNDI_NAME);
						TResult<List<PersonCategory>> res = serv.listAll(TedrosContext.getLoggedUser().getAccessToken(), PersonCategory.class);
						if(res.getState().equals(TState.SUCCESS)) {
							if(res.getValue().isEmpty())
								return new Response(NO_DATA_FOUND_MESSAGE);
							return new Response(SUSCESS_MESSAGE, res.getValue()); 
						}
						
					} catch (NamingException e) {
						LOGGER.error(e.getMessage(), e);
						return new Response(EXCEPTION_MESSAGE + e.getMessage());
					}
					
					return new Response(FAILURE_MESSAGE);
				});
			
	}

}

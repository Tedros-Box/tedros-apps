/**
 * 
 */
package org.tedros.person.ai.function;

import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Empty;
import org.tedros.ai.openai.model.ToolCallResult;
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
							if(res.getValue().isEmpty()) {								
								return ToolCallResult.builder()
										.message("No person categories found.")
										.result(Map.of(
							                    STATUS, ERROR,
							                    ACTION, "no_person_categories_found",
							                    ERROR_MESSAGE, "No person categories available in the system."
							                ))
										.build();
							}
							
							return ToolCallResult.builder()
									.message("Person categories retrieved successfully.")
									.result(Map.of(
						                    STATUS, SUCCESS,
						                    ACTION, "person_categories_listed",
						                    SYSTEM_INSTRUCTION, "Person categories listed successfully. "
						                    		+ "Do not retry again. Proceed with the user's request.",
						                    "person_categories", res.getValue()
						                ))
									.build(); 
						}
						
					} catch (NamingException e) {
						LOGGER.error(e.getMessage(), e);
						return ToolCallResult.builder()
								.message("Error listing person categories: " + e.getMessage())
								.result(Map.of(
					                    STATUS, ERROR,
					                    ACTION, "person_categories_list_error",
					                    ERROR_MESSAGE, e.getMessage()
					                ))
								.build();
					}
					
					return ToolCallResult.builder()
							.message("No person categories found.")
							.result(Map.of(
				                    STATUS, ERROR,
				                    ACTION, "no_person_categories_found",
				                    ERROR_MESSAGE, "No person categories available in the system."
				                ))
							.build();
				});
			
	}

}

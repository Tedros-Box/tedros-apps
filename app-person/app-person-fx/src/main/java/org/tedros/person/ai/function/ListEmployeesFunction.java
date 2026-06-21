/**
 * 
 */
package org.tedros.person.ai.function;

import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.ToolCallResult;
import org.tedros.ai.function.model.Empty;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.person.ejb.controller.IEmployeeController;
import org.tedros.person.model.Employee;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.util.TLoggerUtil;

/**
 * @author Davis Gordon
 *
 */
public class ListEmployeesFunction extends TFunction<Empty> {

	private static final String NONE_EMPLOYEE_FOUND = "None employee found";

	private static final Logger LOGGER = TLoggerUtil.getLogger(ListEmployeesFunction.class);
	
	public static final String NAME = "list_all_employees";
	
	public static final String DESCRIPTION = "Retrieves a complete list of all registered employees in the system. "
			+ "Returns their details, including their internal IDs. Use this if you need to find an employee but don't "
			+ "have specific search criteria, or if you need to run an operation across multiple employees.";

	public ListEmployeesFunction() {
		super(NAME, DESCRIPTION, Empty.class, 
				v->{
										
					LOGGER.info("Listing all employess");
					
					try(TEjbServiceLocator loc = TEjbServiceLocator.getInstance()) {
						IEmployeeController serv = loc.lookup(IEmployeeController.JNDI_NAME);
						TResult<List<Employee>> res = serv.listAll(TedrosContext.getLoggedUser().getAccessToken(), Employee.class);
						if(res.getState().equals(TState.SUCCESS)) {
							if(res.getValue().isEmpty()) {
								return ToolCallResult.builder()
										.message(NONE_EMPLOYEE_FOUND)
										.result(Map.of(
							                    STATUS, ERROR,
							                    ACTION, "none_employee_found",
							                    ERROR_MESSAGE, NONE_EMPLOYEE_FOUND
							                ))
										.build();
							}
							return ToolCallResult.builder()
									.message("Employees found successfully.")
									.result(Map.of(
						                    STATUS, SUCCESS,
						                    ACTION, "employees_found",
						                    SYSTEM_INSTRUCTION, "Employees found successfully. "
						                    		+ "Do not retry again. Proceed with the user's request.",
						                    "employees",  res.getValue()))
									
									.build(); 
						}
						
					} catch (NamingException e) {
						LOGGER.error(e.getMessage(), e);
						return ToolCallResult.builder()
								.message("Error listing employees: " + e.getMessage())
								.result(Map.of(
					                    STATUS, ERROR,
					                    ACTION, "employee_listing_failed",
					                    ERROR_MESSAGE, "Error listing employees: " + e.getMessage()))
								.build();
					}
					
					return ToolCallResult.builder()
							.message(NONE_EMPLOYEE_FOUND)
							.result(Map.of(
				                    STATUS, ERROR,
				                    ACTION, "none_employees_found",
				                    ERROR_MESSAGE, NONE_EMPLOYEE_FOUND
				                ))
							.build();
				});
			
	}

}

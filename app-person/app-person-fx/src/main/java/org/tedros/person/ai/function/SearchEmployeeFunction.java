/**
 * 
 */
package org.tedros.person.ai.function;

import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.ToolCallResult;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.person.ejb.controller.IEmployeeController;
import org.tedros.person.model.Employee;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TJoinType;
import org.tedros.server.query.TSelect;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.util.TLoggerUtil;

/**
 * @author Davis Gordon
 *
 */
public class SearchEmployeeFunction extends TFunction<SearchEmployee> {
	
	private static final String NO_EMPLOYEE_FOUND_MATCHING_THE_CRITERIA = "No employee found matching the criteria.";

	private static final Logger LOGGER = TLoggerUtil.getLogger(SearchEmployeeFunction.class);
	
	public static final String NAME = "search_employees";
	public static final String DESCRIPTION = "Searches the system for an employee by name or document number. "
			+ "Use this tool to find an employee's exact details, including their numerical ID ('id' or 'tedrosUserId'), "
			+ "which is required as input for other tools like 'get_employee_activities' or 'search_gmuds'.";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SearchEmployeeFunction() {
		super(NAME, DESCRIPTION, SearchEmployee.class, 
				v->{
										
					LOGGER.info("Searching Persons with parameters: {}", v);
					
					TSelect sel = new TSelect(Employee.class);
							
					
					if(StringUtils.isNotBlank(v.getName())) {
						sel.addOrCondition("name", TCompareOp.LIKE, v.getName().trim().toLowerCase());
					}
					
					if(StringUtils.isNotBlank(v.getDocument())) {
						sel.addJoin(TJoinType.LEFT, TSelect.ALIAS, "documents", "docs");
						sel.addOrCondition("docs", "value", TCompareOp.LIKE, v.getDocument().trim().toLowerCase());
						sel.addOrCondition("docs", "content", TCompareOp.LIKE, v.getDocument().trim().toLowerCase());
					}
					
					try(TEjbServiceLocator loc = TEjbServiceLocator.getInstance()) {
						IEmployeeController serv = loc.lookup(IEmployeeController.JNDI_NAME);
						TResult<List<Employee>> res = serv.search(TedrosContext.getLoggedUser().getAccessToken(), sel);
						if(res.getState().equals(TState.SUCCESS)) {
							if(res.getValue().isEmpty()) {
								return ToolCallResult.builder()
										.message(NO_EMPLOYEE_FOUND_MATCHING_THE_CRITERIA)
										.result(Map.of(
							                    STATUS, ERROR,
							                    ACTION, "no_employee_found",
							                    ERROR_MESSAGE, NO_EMPLOYEE_FOUND_MATCHING_THE_CRITERIA
							                ))
										.build();
							}
							return ToolCallResult.builder()
									.message("Employee found successfully.")
									.result(Map.of(
						                    STATUS, SUCCESS,
						                    ACTION, "employee_found",
						                    SYSTEM_INSTRUCTION, "Employees found successfully. "
						                    		+ "Do not retry again. Proceed with the user's request.",
						                    "employees",  res.getValue()))
									
									.build(); 
						}
						
					} catch (NamingException e) {
						LOGGER.error(e.getMessage(), e);
						return ToolCallResult.builder()
								.message("Error searching employees: " + e.getMessage())
								.result(Map.of(
					                    STATUS, ERROR,
					                    ACTION, "employee_search_failed",
					                    ERROR_MESSAGE, "Error searching employees: " + e.getMessage()))
								.build();
					}
					
					return ToolCallResult.builder()
							.message(NO_EMPLOYEE_FOUND_MATCHING_THE_CRITERIA)
							.result(Map.of(
				                    STATUS, ERROR,
				                    ACTION, "no_employees_found",
				                    ERROR_MESSAGE, NO_EMPLOYEE_FOUND_MATCHING_THE_CRITERIA
				                ))
							.build();
				});
			
	}

}

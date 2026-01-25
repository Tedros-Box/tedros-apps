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
import org.tedros.core.context.TedrosContext;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.person.ejb.controller.IPersonTypeController;
import org.tedros.person.model.ClientCompanyType;
import org.tedros.person.model.CustomerType;
import org.tedros.person.model.LegalType;
import org.tedros.person.model.MemberType;
import org.tedros.person.model.NaturalType;
import org.tedros.person.model.PersonType;
import org.tedros.person.model.PhilanthropeType;
import org.tedros.person.model.StaffType;
import org.tedros.person.model.VoluntaryType;
import org.tedros.server.query.TSelect;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.util.TLoggerUtil;

/**
 * @author Davis Gordon
 *
 */
public class ListPersonTypeFunction extends TFunction<ClassificationParam> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(ListPersonTypeFunction.class);
	
	public static final String NAME = "list_person_types";
	public static final String DESCRIPTION = "List all types that can be given to a person";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ListPersonTypeFunction() {
		super(NAME, DESCRIPTION, ClassificationParam.class, 
				v->{
					LOGGER.info("Listing all person types with classification: {}", v.getClassification());
					
					TSelect sel = null;
					if(v.getClassification()==null)
						sel = new TSelect(PersonType.class);
					else {
						switch(v.getClassification()) {
						case ALL:
							sel = new TSelect(PersonType.class);
							break;
						case CLIENT_COMPANY:
							sel = new TSelect(ClientCompanyType.class);
							break;
						case CUSTOMER:
							sel = new TSelect(CustomerType.class);
							break;
						case EMPLOYEE:
							sel = new TSelect(StaffType.class);
							break;
						case LEGAL_PERSON:
							sel = new TSelect(LegalType.class);
							break;
						case MEMBER:
							sel = new TSelect(MemberType.class);
							break;
						case NATURAL_PERSON:
							sel = new TSelect(NaturalType.class);
							break;
						case PHILANTHROPE:
							sel = new TSelect(PhilanthropeType.class);
							break;
						case VOLUNTARY:
							sel = new TSelect(VoluntaryType.class);
							break;
						default:
							break;
						}
					}
										
					try(TEjbServiceLocator loc = TEjbServiceLocator.getInstance()) {
						IPersonTypeController serv = loc.lookup(IPersonTypeController.JNDI_NAME);
						TResult<List<PersonType>> res = serv.search(TedrosContext.getLoggedUser().getAccessToken(), sel);
						if(res.getState().equals(TState.SUCCESS)) {
							if(res.getValue().isEmpty()) {
								return ToolCallResult.builder()
										.message("No person types found.")
										.result(Map.of(
							                    STATUS, ERROR,
							                    ACTION, "no_person_types_found",
							                    ERROR_MESSAGE, "No person types available in the system."
							                ))
										.build();
							}
							
							return ToolCallResult.builder()
									.message("Person types retrieved successfully.")
									.result(Map.of(
						                    STATUS, SUCCESS,
						                    ACTION, "person_types_listed",
						                    SYSTEM_INSTRUCTION, "Person types listed successfully. "
						                    		+ "Do not retry again. Proceed with the user's request.",
						                    "person_types", res.getValue()
						                ))
									.build(); 
						}
						
					} catch (NamingException e) {
						LOGGER.error(e.getMessage(), e);
						return ToolCallResult.builder()
								.message("Error listing person types: " + e.getMessage())
								.result(Map.of(
					                    STATUS, ERROR,
					                    ACTION, "person_types_list_error",
					                    ERROR_MESSAGE, e.getMessage()
					                ))
								.build();
					}
					
					return ToolCallResult.builder()
							.message("No person types found.")
							.result(Map.of(
				                    STATUS, ERROR,
				                    ACTION, "no_person_types_found",
				                    ERROR_MESSAGE, "No person types available in the system."
				                ))
							.build();
				});
			
	}

}

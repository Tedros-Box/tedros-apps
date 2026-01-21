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
import org.tedros.ai.openai.model.ToolCallResult;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.person.ejb.controller.IPersonController;
import org.tedros.person.model.ClientCompany;
import org.tedros.person.model.Customer;
import org.tedros.person.model.Employee;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.model.Member;
import org.tedros.person.model.NaturalPerson;
import org.tedros.person.model.Person;
import org.tedros.person.model.Philanthrope;
import org.tedros.person.model.Voluntary;
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
public class SearchPersonFunction extends TFunction<Search> {
	
	private static final String NO_PERSONS_FOUND_MATCHING_THE_CRITERIA = "No persons found matching the criteria.";

	private static final Logger LOGGER = TLoggerUtil.getLogger(SearchPersonFunction.class);
	
	public static final String NAME = "search_person";
	public static final String DESCRIPTION = """
			Search for people, companies, employees, or members in the database. 
            Use this tool when the user asks to find, look up, or retrieve information about a specific entity
            based on attributes like name, document, contact, or location.
			""";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SearchPersonFunction() {
		super(NAME, DESCRIPTION, Search.class, 
				v->{
										
					LOGGER.info("Searching Persons with parameters: {}", v);
					
					TSelect sel = null;
					if(v.getClassification()==null)
						sel = new TSelect(Person.class);
					else {
						switch(v.getClassification()) {
						case ALL:
							sel = new TSelect(Person.class);
							break;
						case CLIENT_COMPANY:
							sel = new TSelect(ClientCompany.class);
							break;
						case CUSTOMER:
							sel = new TSelect(Customer.class);
							break;
						case EMPLOYEE:
							sel = new TSelect(Employee.class);
							break;
						case LEGAL_PERSON:
							sel = new TSelect(LegalPerson.class);
							break;
						case MEMBER:
							sel = new TSelect(Member.class);
							break;
						case NATURAL_PERSON:
							sel = new TSelect(NaturalPerson.class);
							break;
						case PHILANTHROPE:
							sel = new TSelect(Philanthrope.class);
							break;
						case VOLUNTARY:
							sel = new TSelect(Voluntary.class);
							break;
						default:
							break;
						}
					}
					
					if(StringUtils.isNotBlank(v.getName())) {
						sel.addOrCondition("name", TCompareOp.LIKE, v.getName().trim().toLowerCase());
					}
					
					if(StringUtils.isNotBlank(v.getContact())) {
						sel.addJoin(TJoinType.LEFT, TSelect.ALIAS, "contacts", "cts");
						sel.addOrCondition("cts", "value", TCompareOp.LIKE, v.getContact().trim().toLowerCase());
					}
					
					if(StringUtils.isNotBlank(v.getDocument())) {
						sel.addJoin(TJoinType.LEFT, TSelect.ALIAS, "documents", "docs");
						sel.addOrCondition("docs", "value", TCompareOp.LIKE, v.getDocument().trim().toLowerCase());
						sel.addOrCondition("docs", "content", TCompareOp.LIKE, v.getDocument().trim().toLowerCase());
					}
					
					if(v.getLocation()!=null) {
						Location l = v.getLocation();
						if(StringUtils.isNotBlank(l.getAdminArea()) || StringUtils.isNotBlank(l.getCity()) 
								|| StringUtils.isNotBlank(l.getCountryIso2Code()) || StringUtils.isNotBlank(l.getNeighborhood())) {
							sel.addJoin(TJoinType.LEFT, TSelect.ALIAS, "address", "adr");
							
							if(StringUtils.isNotBlank(l.getNeighborhood()))
								sel.addAndCondition("adr", "neighborhood", TCompareOp.LIKE, l.getNeighborhood().trim().toLowerCase());
							if(StringUtils.isNotBlank(l.getCountryIso2Code())) {
								sel.addJoin(TJoinType.LEFT, "adr", "country", "cnt");
								sel.addAndCondition("cnt", "iso2Code", TCompareOp.EQUAL, l.getCountryIso2Code().trim().toUpperCase());
							}
							if(StringUtils.isNotBlank(l.getAdminArea())) {
								sel.addJoin(TJoinType.LEFT, "adr", "adminArea", "ada");
								sel.addAndCondition("ada", "name", TCompareOp.LIKE, l.getAdminArea().trim().toLowerCase());
								if(StringUtils.isNotBlank(l.getCountryIso2Code()))
									sel.addAndCondition("ada", "countryIso2Code", TCompareOp.EQUAL, l.getCountryIso2Code().trim().toUpperCase());
							}
							if(StringUtils.isNotBlank(l.getCity())) {
								sel.addJoin(TJoinType.LEFT, "adr", "city", "cty");
								sel.addAndCondition("cty", "name", TCompareOp.LIKE, l.getCity().trim().toLowerCase());
							}
						}
					}
					
					
					try(TEjbServiceLocator loc = TEjbServiceLocator.getInstance()) {
						IPersonController serv = loc.lookup(IPersonController.JNDI_NAME);
						TResult<List<Person>> res = serv.search(TedrosContext.getLoggedUser().getAccessToken(), sel);
						if(res.getState().equals(TState.SUCCESS)) {
							if(res.getValue().isEmpty()) {
								return ToolCallResult.builder()
										.message(NO_PERSONS_FOUND_MATCHING_THE_CRITERIA)
										.result(Map.of(
							                    STATUS, ERROR,
							                    ACTION, "no_persons_found",
							                    ERROR_MESSAGE, NO_PERSONS_FOUND_MATCHING_THE_CRITERIA
							                ))
										.build();
							}
							return ToolCallResult.builder()
									.message("Persons found successfully.")
									.result(Map.of(
						                    STATUS, SUCCESS,
						                    ACTION, "persons_found",
						                    SYSTEM_INSTRUCTION, "Persons found successfully. "
						                    		+ "Do not retry again. Proceed with the user's request.",
						                    "persons",  res.getValue()))
									
									.build(); 
						}
						
					} catch (NamingException e) {
						LOGGER.error(e.getMessage(), e);
						return ToolCallResult.builder()
								.message("Error searching persons: " + e.getMessage())
								.result(Map.of(
					                    STATUS, ERROR,
					                    ACTION, "person_search_failed",
					                    ERROR_MESSAGE, "Error searching persons: " + e.getMessage()))
								.build();
					}
					
					return ToolCallResult.builder()
							.message(NO_PERSONS_FOUND_MATCHING_THE_CRITERIA)
							.result(Map.of(
				                    STATUS, ERROR,
				                    ACTION, "no_persons_found",
				                    ERROR_MESSAGE, NO_PERSONS_FOUND_MATCHING_THE_CRITERIA
				                ))
							.build();
				});
			
	}

}

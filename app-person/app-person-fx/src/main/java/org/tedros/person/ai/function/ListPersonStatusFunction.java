/**
 * 
 */
package org.tedros.person.ai.function;

import java.util.List;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.person.ejb.controller.IPersonStatusController;
import org.tedros.person.model.ClientCompanyStatus;
import org.tedros.person.model.CustomerStatus;
import org.tedros.person.model.EmployeeStatus;
import org.tedros.person.model.LegalStatus;
import org.tedros.person.model.MemberStatus;
import org.tedros.person.model.NaturalStatus;
import org.tedros.person.model.PersonStatus;
import org.tedros.person.model.PhilanthropeStatus;
import org.tedros.person.model.VoluntaryStatus;
import org.tedros.server.query.TSelect;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.util.TLoggerUtil;

/**
 * @author Davis Gordon
 *
 */
public class ListPersonStatusFunction extends TFunction<ClassificationParam> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(ListPersonStatusFunction.class);
	
	public static final String NAME = "list_statuses_person";
	public static final String DESCRIPTION = "List all statuses that a person can receive";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ListPersonStatusFunction() {
		super(NAME, DESCRIPTION, ClassificationParam.class, 
				v->{
										
					LOGGER.info("Listing all person statuses with classification: {}", v.getClassification());
					
					TSelect sel = null;
					if(v.getClassification()==null)
						sel = new TSelect(PersonStatus.class);
					else {
						switch(v.getClassification()) {
						case ALL:
							sel = new TSelect(PersonStatus.class);
							break;
						case CLIENT_COMPANY:
							sel = new TSelect(ClientCompanyStatus.class);
							break;
						case CUSTOMER:
							sel = new TSelect(CustomerStatus.class);
							break;
						case EMPLOYEE:
							sel = new TSelect(EmployeeStatus.class);
							break;
						case LEGAL_PERSON:
							sel = new TSelect(LegalStatus.class);
							break;
						case MEMBER:
							sel = new TSelect(MemberStatus.class);
							break;
						case NATURAL_PERSON:
							sel = new TSelect(NaturalStatus.class);
							break;
						case PHILANTHROPE:
							sel = new TSelect(PhilanthropeStatus.class);
							break;
						case VOLUNTARY:
							sel = new TSelect(VoluntaryStatus.class);
							break;
						default:
							break;
						}
					}
					
					try(TEjbServiceLocator loc = TEjbServiceLocator.getInstance()) {
						IPersonStatusController serv = loc.lookup(IPersonStatusController.JNDI_NAME);
						TResult<List<PersonStatus>> res = serv.search(TedrosContext.getLoggedUser().getAccessToken(), sel);
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

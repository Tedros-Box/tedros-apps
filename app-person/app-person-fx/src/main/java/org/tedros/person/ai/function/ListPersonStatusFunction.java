/**
 * 
 */
package org.tedros.person.ai.function;

import java.util.List;

import javax.naming.NamingException;

import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.service.remote.ServiceLocator;
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

/**
 * @author Davis Gordon
 *
 */
public class ListPersonStatusFunction extends TFunction<ClassificationParam> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ListPersonStatusFunction() {
		super("list_statuses_person", "List all statuses that a person can receive", ClassificationParam.class, 
				v->{
					TSelect sel = null;;
					if(v.getClassification()==null)
						sel = new TSelect(PersonStatus.class);
					else
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
					
					ServiceLocator loc = ServiceLocator.getInstance();
					try {
						IPersonStatusController serv = loc.lookup(IPersonStatusController.JNDI_NAME);
						TResult<List<PersonStatus>> res = serv.search(TedrosContext.getLoggedUser().getAccessToken(), sel);
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

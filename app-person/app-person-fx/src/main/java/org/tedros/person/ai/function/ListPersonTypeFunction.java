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

/**
 * @author Davis Gordon
 *
 */
public class ListPersonTypeFunction extends TFunction<ClassificationParam> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ListPersonTypeFunction() {
		super("list_types_person", "List all types that can be given to a person", ClassificationParam.class, 
				v->{
					TSelect sel = null;;
					if(v.getClassification()==null)
						sel = new TSelect(PersonType.class);
					else
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
					
					ServiceLocator loc = ServiceLocator.getInstance();
					try {
						IPersonTypeController serv = loc.lookup(IPersonTypeController.JNDI_NAME);
						TResult<List<PersonType>> res = serv.search(TedrosContext.getLoggedUser().getAccessToken(), sel);
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

/**
 * 
 */
package org.tedros.person.ai.function;

import java.util.List;

import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.service.remote.ServiceLocator;
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

/**
 * @author Davis Gordon
 *
 */
public class SearchPersonFunction extends TFunction<Search> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SearchPersonFunction() {
		super("search_person", "Searches for persons", Search.class, 
				v->{
					TSelect sel = null;;
					if(v.getClassification()==null)
						sel = new TSelect(Person.class);
					else
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
					
					ServiceLocator loc = ServiceLocator.getInstance();
					try {
						IPersonController serv = loc.lookup(IPersonController.JNDI_NAME);
						TResult<List<Person>> res = serv.search(TedrosContext.getLoggedUser().getAccessToken(), sel);
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

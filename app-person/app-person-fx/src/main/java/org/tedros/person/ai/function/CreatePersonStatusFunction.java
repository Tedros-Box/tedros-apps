package org.tedros.person.ai.function;

import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.person.model.ClientCompanyStatus;
import org.tedros.person.model.CustomerStatus;
import org.tedros.person.model.EmployeeStatus;
import org.tedros.person.model.LegalStatus;
import org.tedros.person.model.MemberStatus;
import org.tedros.person.model.NaturalStatus;
import org.tedros.person.model.PersonStatus;
import org.tedros.person.model.PhilanthropeStatus;
import org.tedros.person.model.VoluntaryStatus;
import org.tedros.person.module.company.LegalPersonModule;
import org.tedros.person.module.company.model.CompanyStatusMV;
import org.tedros.person.module.company.model.EmployeeStatusMV;
import org.tedros.person.module.customer.CustomerModule;
import org.tedros.person.module.customer.model.ClientCompanyStatusMV;
import org.tedros.person.module.customer.model.CustomerStatusMV;
import org.tedros.person.module.individual.IndividualModule;
import org.tedros.person.module.individual.model.IndividualStatusMV;
import org.tedros.person.module.individual.model.MemberStatusMV;
import org.tedros.person.module.individual.model.PhilanthropeStatusMV;
import org.tedros.person.module.individual.model.VoluntaryStatusMV;

import javafx.application.Platform;

public class CreatePersonStatusFunction extends TFunction<PersonAttributeParam>{

	public CreatePersonStatusFunction() {
		super("create_person_status", "create a person status register", PersonAttributeParam.class, 
				v ->{
					
					TedrosAppManager mng = TedrosAppManager.getInstance();
					
					if(v.getClassification()==null)
						return new Response("The field 'classification' must be filled with an acceptable value!");
					
					switch(v.getClassification()) {
					case CLIENT_COMPANY:
						Platform.runLater(()->{
							ClientCompanyStatus e = new ClientCompanyStatus();
							setProperties(v, e);
							ClientCompanyStatusMV mv = new ClientCompanyStatusMV((ClientCompanyStatus) e);
							mng.loadInModule(CustomerModule.class, mv);
						});
						break;
					case CUSTOMER:
						Platform.runLater(()->{
							CustomerStatus e = new CustomerStatus();
							setProperties(v, e);
							CustomerStatusMV mv = new CustomerStatusMV((CustomerStatus) e);
							mng.loadInModule(CustomerModule.class, mv);
						});					
						break;
					case EMPLOYEE:
						Platform.runLater(()->{
							EmployeeStatus e = new EmployeeStatus();
							setProperties(v, e);
							EmployeeStatusMV mv = new EmployeeStatusMV((EmployeeStatus) e);
							mng.loadInModule(LegalPersonModule.class, mv);
						});
						break;
					case LEGAL_PERSON:
						Platform.runLater(()->{
							LegalStatus e = new LegalStatus();
							setProperties(v, e);
							CompanyStatusMV mv = new CompanyStatusMV((LegalStatus) e);
							mng.loadInModule(LegalPersonModule.class, mv);
						});
					break;
					case MEMBER:
						Platform.runLater(()->{
							MemberStatus e = new MemberStatus();
							setProperties(v, e);
							MemberStatusMV mv = new MemberStatusMV((MemberStatus) e);
							mng.loadInModule(IndividualModule.class, mv);
						});
						break;
					case NATURAL_PERSON:
						Platform.runLater(()->{
							NaturalStatus e = new NaturalStatus();
							setProperties(v, e);
							IndividualStatusMV mv = new IndividualStatusMV((NaturalStatus) e);
							mng.loadInModule(IndividualModule.class, mv);
						});
						break;
					case PHILANTHROPE:
						Platform.runLater(()->{
							PhilanthropeStatus e = new PhilanthropeStatus();
							setProperties(v, e);
							PhilanthropeStatusMV mv = new PhilanthropeStatusMV((PhilanthropeStatus) e);
							mng.loadInModule(IndividualModule.class, mv);
						});
						break;
					case VOLUNTARY:
						Platform.runLater(()->{
							VoluntaryStatus e = new VoluntaryStatus();
							setProperties(v, e);
							VoluntaryStatusMV mv = new VoluntaryStatusMV((VoluntaryStatus) e);
							mng.loadInModule(IndividualModule.class, mv);
						});
						break;
					default:
						break;
					}		
					return new Response("Success");
				});
	}

	private static void setProperties(PersonAttributeParam v, PersonStatus entity) {
		entity.setCode(v.getCode());
		entity.setName(v.getName());
		entity.setDescription(v.getDescription());
	}

}

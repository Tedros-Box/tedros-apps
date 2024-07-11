package org.tedros.person.ai.function;

import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.person.model.ClientCompanyType;
import org.tedros.person.model.CustomerType;
import org.tedros.person.model.LegalType;
import org.tedros.person.model.MemberType;
import org.tedros.person.model.NaturalType;
import org.tedros.person.model.PersonType;
import org.tedros.person.model.PhilanthropeType;
import org.tedros.person.model.StaffType;
import org.tedros.person.model.VoluntaryType;
import org.tedros.person.module.company.LegalPersonModule;
import org.tedros.person.module.company.model.CompanyTypeMV;
import org.tedros.person.module.company.model.StaffTypeMV;
import org.tedros.person.module.customer.CustomerModule;
import org.tedros.person.module.customer.model.ClientCompanyTypeMV;
import org.tedros.person.module.customer.model.CustomerTypeMV;
import org.tedros.person.module.individual.IndividualModule;
import org.tedros.person.module.individual.model.IndividualTypeMV;
import org.tedros.person.module.individual.model.MemberTypeMV;
import org.tedros.person.module.individual.model.PhilanthropeTypeMV;
import org.tedros.person.module.individual.model.VoluntaryTypeMV;

import javafx.application.Platform;

public class CreatePersonTypeFunction extends TFunction<PersonAttributeParam>{

	public CreatePersonTypeFunction() {
		super("create_person_type", "create a person type register", PersonAttributeParam.class, 
				v ->{
					
					TedrosAppManager mng = TedrosAppManager.getInstance();
					
					if(v.getClassification()==null)
						return new Response("The field 'classification' must be filled with an acceptable value!");
					
					switch(v.getClassification()) {
					case CLIENT_COMPANY:
						Platform.runLater(()->{
							ClientCompanyType e = new ClientCompanyType();
							setProperties(v, e);
							ClientCompanyTypeMV mv = new ClientCompanyTypeMV((ClientCompanyType) e);
							mng.loadInModule(CustomerModule.class, mv);
						});
						break;
					case CUSTOMER:
						Platform.runLater(()->{
							CustomerType e = new CustomerType();
							setProperties(v, e);
							CustomerTypeMV mv = new CustomerTypeMV((CustomerType) e);
							mng.loadInModule(CustomerModule.class, mv);
						});					
						break;
					case EMPLOYEE:
						Platform.runLater(()->{
							StaffType e = new StaffType();
							setProperties(v, e);
							StaffTypeMV mv = new StaffTypeMV((StaffType) e);
							mng.loadInModule(LegalPersonModule.class, mv);
						});
						break;
					case LEGAL_PERSON:
						Platform.runLater(()->{
							LegalType e = new LegalType();
							setProperties(v, e);
							CompanyTypeMV mv = new CompanyTypeMV((LegalType) e);
							mng.loadInModule(LegalPersonModule.class, mv);
						});
					break;
					case MEMBER:
						Platform.runLater(()->{
							MemberType e = new MemberType();
							setProperties(v, e);
							MemberTypeMV mv = new MemberTypeMV((MemberType) e);
							mng.loadInModule(IndividualModule.class, mv);
						});
						break;
					case NATURAL_PERSON:
						Platform.runLater(()->{
							NaturalType e = new NaturalType();
							setProperties(v, e);
							IndividualTypeMV mv = new IndividualTypeMV((NaturalType) e);
							mng.loadInModule(IndividualModule.class, mv);
						});
						break;
					case PHILANTHROPE:
						Platform.runLater(()->{
							PhilanthropeType e = new PhilanthropeType();
							setProperties(v, e);
							PhilanthropeTypeMV mv = new PhilanthropeTypeMV((PhilanthropeType) e);
							mng.loadInModule(IndividualModule.class, mv);
						});
						break;
					case VOLUNTARY:
						Platform.runLater(()->{
							VoluntaryType e = new VoluntaryType();
							setProperties(v, e);
							VoluntaryTypeMV mv = new VoluntaryTypeMV((VoluntaryType) e);
							mng.loadInModule(IndividualModule.class, mv);
						});
						break;
					default:
						return new Response("The field 'classification' must be filled with an acceptable value!");						
					}		
					return new Response("Success");
				});
	}

	private static void setProperties(PersonAttributeParam v, PersonType entity) {
		entity.setCode(v.getCode());
		entity.setName(v.getName());
		entity.setDescription(v.getDescription());
	}

}

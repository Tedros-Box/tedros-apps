package org.tedros.person.ai.function;

import org.slf4j.Logger;
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
import org.tedros.util.TLoggerUtil;

import javafx.application.Platform;

public class CreatePersonStatusFunction extends TFunction<PersonAttributeParam>{
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(CreatePersonStatusFunction.class);
	
	public static final String NAME = "create_person_status";
	public static final String DESCRIPTION = "Opens the administration view to create a new 'Person Status' record. " +
            "A Status defines the current state or lifecycle of an entity (e.g., 'Active', 'Blocked', 'On Vacation', 'Suspended'). " +
            "CRITICAL: You MUST provide the 'classification' parameter (e.g., EMPLOYEE, CUSTOMER) so the system knows which module to open.";

	public CreatePersonStatusFunction() {
		super(NAME, DESCRIPTION, PersonAttributeParam.class, 
				v ->{
					
					LOGGER.info("Creating Person Status: {}", v);
					
					TedrosAppManager mng = TedrosAppManager.getInstance();
					
					if(v.getClassification()==null)
						return new Response("The field 'classification' must be filled with an acceptable value!");
					
					switch(v.getClassification()) {
					case CLIENT_COMPANY:
						Platform.runLater(()->{
							try {
								ClientCompanyStatus e = new ClientCompanyStatus();
								setProperties(v, e);
								ClientCompanyStatusMV mv = new ClientCompanyStatusMV(e);
								mng.loadInModule(CustomerModule.class, mv);
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
							}
						});
						break;
					case CUSTOMER:
						Platform.runLater(()->{
							try {
								CustomerStatus e = new CustomerStatus();
								setProperties(v, e);
								CustomerStatusMV mv = new CustomerStatusMV(e);
								mng.loadInModule(CustomerModule.class, mv);
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
							}
						});					
						break;
					case EMPLOYEE:
						Platform.runLater(()->{
							try {
								EmployeeStatus e = new EmployeeStatus();
								setProperties(v, e);
								EmployeeStatusMV mv = new EmployeeStatusMV(e);
								mng.loadInModule(LegalPersonModule.class, mv);
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
							}
						});
						break;
					case LEGAL_PERSON:
						Platform.runLater(()->{
							try {
								LegalStatus e = new LegalStatus();
								setProperties(v, e);
								CompanyStatusMV mv = new CompanyStatusMV(e);
								mng.loadInModule(LegalPersonModule.class, mv);
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
							}
						});
					break;
					case MEMBER:
						Platform.runLater(()->{
							try {
								MemberStatus e = new MemberStatus();
								setProperties(v, e);
								MemberStatusMV mv = new MemberStatusMV(e);
								mng.loadInModule(IndividualModule.class, mv);
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
							}
						});
						break;
					case NATURAL_PERSON:
						Platform.runLater(()->{
							try {
								NaturalStatus e = new NaturalStatus();
								setProperties(v, e);
								IndividualStatusMV mv = new IndividualStatusMV(e);
								mng.loadInModule(IndividualModule.class, mv);
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
							}
						});
						break;
					case PHILANTHROPE:
						Platform.runLater(()->{
							try {
								PhilanthropeStatus e = new PhilanthropeStatus();
								setProperties(v, e);
								PhilanthropeStatusMV mv = new PhilanthropeStatusMV(e);
								mng.loadInModule(IndividualModule.class, mv);
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
							}
						});
						break;
					case VOLUNTARY:
						Platform.runLater(()->{
							try {
								VoluntaryStatus e = new VoluntaryStatus();
								setProperties(v, e);
								VoluntaryStatusMV mv = new VoluntaryStatusMV(e);
								mng.loadInModule(IndividualModule.class, mv);
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
							}
						});
						break;
					default:
						break;
					}		
					return new Response(SUSCESS_MESSAGE);
				});
	}

	private static void setProperties(PersonAttributeParam v, PersonStatus entity) {
		entity.setCode(v.getCode());
		entity.setName(v.getName());
		entity.setDescription(v.getDescription());
	}

}

package org.tedros.person.ai.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.openai.model.ToolCallResult;
import org.tedros.core.ITModule;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.model.ITModelView;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CreatePersonStatusFunction extends TFunction<PersonAttributeParamList>{
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(CreatePersonStatusFunction.class);
	
	public static final String NAME = "create_person_statuses";
	public static final String DESCRIPTION = "Opens the administration view to create a new 'Person Status' record. " +
            "A Status defines the current state or lifecycle of an entity (e.g., 'Active', 'Blocked', 'On Vacation', 'Suspended'). " +
            "CRITICAL: You MUST provide the 'classification' parameter (e.g., EMPLOYEE, CUSTOMER) so the system knows which module to open.";

	@SuppressWarnings("rawtypes")
	public CreatePersonStatusFunction() {
		super(NAME, DESCRIPTION, PersonAttributeParamList.class, 
				v ->{
					
					LOGGER.info("Creating Person Status: {}", v);
					
					TedrosAppManager mng = TedrosAppManager.getInstance();
					
					Map<Classification, ObservableList<ITModelView>> models = new HashMap<>();
					for(Classification c : Classification.values()) {
						models.put(c, FXCollections.observableArrayList());
					}
					
					List<String> failedMessages = new ArrayList<>();
					
					for(PersonAttributeParam p : v.getAttributes()) {
						if(p.getClassification()==null) {
							failedMessages.add("The field 'classification' must be filled with an acceptable value.");
							continue;
						}	
						
						switch(p.getClassification()) {
						
						case CLIENT_COMPANY:
							
							try {
								ClientCompanyStatus e = new ClientCompanyStatus();
								setProperties(p, e);
								models.get(p.getClassification()).add(new ClientCompanyStatusMV(e));
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
								failedMessages.add("Error creating Client Company Status: " + e.getMessage());
							}
							break;
							
						case CUSTOMER:
							
							try {
								CustomerStatus e = new CustomerStatus();
								setProperties(p, e);
								models.get(p.getClassification()).add(new CustomerStatusMV(e));
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
								failedMessages.add("Error creating Customer Status: " + e.getMessage());
							}
												
							break;
							
						case EMPLOYEE:
							
							try {
								EmployeeStatus e = new EmployeeStatus();
								setProperties(p, e);
								models.get(p.getClassification()).add(new EmployeeStatusMV(e));
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
								failedMessages.add("Error creating Employee Status: " + e.getMessage());
							}
							
							break;
							
						case LEGAL_PERSON:
							
							try {
								LegalStatus e = new LegalStatus();
								setProperties(p, e);
								models.get(p.getClassification()).add(new CompanyStatusMV(e));
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
								failedMessages.add("Error creating Legal Person Status: " + e.getMessage());
							}
							
						break;
						
						case MEMBER:
							
							try {
								MemberStatus e = new MemberStatus();
								setProperties(p, e);
								models.get(p.getClassification()).add(new MemberStatusMV(e));
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
								failedMessages.add("Error creating Member Status: " + e.getMessage());
							}
							
							break;
						case NATURAL_PERSON:
							
							try {
								NaturalStatus e = new NaturalStatus();
								setProperties(p, e);
								models.get(p.getClassification()).add(new IndividualStatusMV(e));
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
								failedMessages.add("Error creating Natural Person Status: " + e.getMessage());
							}
							
							break;
							
						case PHILANTHROPE:
							
							try {
								PhilanthropeStatus e = new PhilanthropeStatus();
								setProperties(p, e);
								models.get(p.getClassification()).add(new PhilanthropeStatusMV(e));
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
								failedMessages.add("Error creating Philanthrope Status: " + e.getMessage());
							}
							
							break;
						case VOLUNTARY:
							
							try {
								VoluntaryStatus e = new VoluntaryStatus();
								setProperties(p, e);
								models.get(p.getClassification()).add(new VoluntaryStatusMV(e));
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
								failedMessages.add("Error creating Voluntary Status: " + e.getMessage());
							}
							
							break;
							
						default:
							failedMessages.add("The classification '"+p.getClassification()+"' is not recognized or supported.");
							break;
						}
					}
					
					if(!failedMessages.isEmpty()) {
						String combinedMessage = String.join(" ", failedMessages);
						return ToolCallResult.builder()
								.message(combinedMessage)
								.result(Map.of(
					                    STATUS, ERROR,
					                    ACTION, "no_person_status_created",
					                    ERROR_MESSAGE, combinedMessage
					                )).build();
					}
					
					for(Map.Entry<Classification, ObservableList<ITModelView>> entry : models.entrySet()) {
						Classification key = entry.getKey();
						ObservableList<ITModelView> list = entry.getValue();
						if(!list.isEmpty()) {
							Class<? extends ITModule> moduleClass = getModuleClass(key);
							if(moduleClass!=null) {
								Platform.runLater(()->{
									try {
										mng.loadInModule(moduleClass, list);
									} catch (Exception e) {
										LOGGER.error(e.getMessage(), e);
									}
								});
							}
						}
					}
					
					return ToolCallResult.builder()
							.message("Person Status creation screen opened.")
							.result(Map.of(
				                    STATUS, SUCCESS,
				                    ACTION, "person_status_screen_opened",
				                    SYSTEM_INSTRUCTION, "The system has opened the Person Status creation screen. "
					                    	+ "Do not retry again. Inform the user to check the opened screen."
				                ))
							.build();
				});
	}

	private static Class<? extends ITModule> getModuleClass(Classification c) {
		switch(c) {
		case CLIENT_COMPANY, CUSTOMER:
			return CustomerModule.class;
		case EMPLOYEE, LEGAL_PERSON:
			return LegalPersonModule.class;
		case MEMBER, NATURAL_PERSON, PHILANTHROPE, VOLUNTARY:
			return IndividualModule.class;
		default:
			return null;
		}
	}

	private static void setProperties(PersonAttributeParam v, PersonStatus entity) {
		entity.setCode(v.getCode());
		entity.setName(v.getName());
		entity.setDescription(v.getDescription());
	}

}

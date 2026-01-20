package org.tedros.person.ai.function;

import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.openai.model.ToolCallResult;
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
import org.tedros.util.TLoggerUtil;

import javafx.application.Platform;

public class CreatePersonTypeFunction extends TFunction<PersonAttributeParam>{
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(CreatePersonTypeFunction.class);

	public static final String NAME = "create_person_type";
	public static final String DESCRIPTION = "Opens the administration view to create a new 'Person Type'. " +
            "Use this to define structural distinctions or sub-classes of entities. " +
            "Examples: For Employees use 'Full-time', 'Contractor', 'Intern'; " +
            "For Customers use 'Reseller', 'B2B', 'Government'. " +
            "Do NOT use this for lifecycle states (use 'Status') or marketing segments (use 'Category'). " +
            "Requires the 'classification' field.";
	
	public CreatePersonTypeFunction() {
		super(NAME, DESCRIPTION, PersonAttributeParam.class, 
				v ->{
					
					LOGGER.info("Creating Person Type: {}", v);
					
					TedrosAppManager mng = TedrosAppManager.getInstance();
					
					if(v.getClassification()==null)
						return ToolCallResult.builder()
								.message("The field 'classification' must be filled with an acceptable value!")
								.result(Map.of(
					                    STATUS, ERROR,
					                    ACTION, "missing_classification",
					                    INFO_MESSAGE, "The field 'classification' is required to proceed."
					                ))
								.build();
					
					switch(v.getClassification()) {
					case CLIENT_COMPANY:
						Platform.runLater(()->{
							try {
								ClientCompanyType e = new ClientCompanyType();
								setProperties(v, e);
								ClientCompanyTypeMV mv = new ClientCompanyTypeMV(e);
								mng.loadInModule(CustomerModule.class, mv);
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
							}
						});
						break;
					case CUSTOMER:
						Platform.runLater(()->{
							try {
								CustomerType e = new CustomerType();
								setProperties(v, e);
								CustomerTypeMV mv = new CustomerTypeMV(e);
								mng.loadInModule(CustomerModule.class, mv);
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
							}
						});					
						break;
					case EMPLOYEE:
						Platform.runLater(()->{
							try {
								StaffType e = new StaffType();
								setProperties(v, e);
								StaffTypeMV mv = new StaffTypeMV(e);
								mng.loadInModule(LegalPersonModule.class, mv);
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
							}
						});
						break;
					case LEGAL_PERSON:
						Platform.runLater(()->{
							try {
								LegalType e = new LegalType();
								setProperties(v, e);
								CompanyTypeMV mv = new CompanyTypeMV(e);
								mng.loadInModule(LegalPersonModule.class, mv);
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
							}
						});
					break;
					case MEMBER:
						Platform.runLater(()->{
							try {
								MemberType e = new MemberType();
								setProperties(v, e);
								MemberTypeMV mv = new MemberTypeMV(e);
								mng.loadInModule(IndividualModule.class, mv);
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
							}
						});
						break;
					case NATURAL_PERSON:
						Platform.runLater(()->{
							try {
								NaturalType e = new NaturalType();
								setProperties(v, e);
								IndividualTypeMV mv = new IndividualTypeMV(e);
								mng.loadInModule(IndividualModule.class, mv);
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
							}
						});
						break;
					case PHILANTHROPE:
						Platform.runLater(()->{
							try {
								PhilanthropeType e = new PhilanthropeType();
								setProperties(v, e);
								PhilanthropeTypeMV mv = new PhilanthropeTypeMV(e);
								mng.loadInModule(IndividualModule.class, mv);
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
							}
						});
						break;
					case VOLUNTARY:
						Platform.runLater(()->{
							try {
								VoluntaryType e = new VoluntaryType();
								setProperties(v, e);
								VoluntaryTypeMV mv = new VoluntaryTypeMV(e);
								mng.loadInModule(IndividualModule.class, mv);
							} catch (Exception e) {
								LOGGER.error(e.getMessage(), e);
							}
						});
						break;
					default:
						return ToolCallResult.builder()
								.message("The field 'classification' must be filled with an acceptable value!")
								.result(Map.of(
					                    STATUS, ERROR,
					                    ACTION, "invalid_classification",
					                    INFO_MESSAGE, "The provided classification is not recognized."
					                ))
								.build();		
					}		
					return ToolCallResult.builder()
							.message("Person Type creation screen opened.")
							.result(Map.of(
				                    STATUS, SUCCESS,
				                    ACTION, "person_type_screen_opened",
				                    INFO_MESSAGE, CONTENT_LOADED_IN_VIEW_FOR_USER_REVIEW_DO_NOT_RETRY
				                ))
							.build();
				});
	}

	private static void setProperties(PersonAttributeParam v, PersonType entity) {
		entity.setCode(v.getCode());
		entity.setName(v.getName());
		entity.setDescription(v.getDescription());
	}

}

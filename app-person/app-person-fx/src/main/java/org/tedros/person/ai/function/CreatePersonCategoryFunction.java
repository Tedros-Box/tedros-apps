package org.tedros.person.ai.function;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.person.model.PersonCategory;
import org.tedros.person.module.category.CategoryModule;
import org.tedros.person.module.category.model.CategoryMV;
import org.tedros.util.TLoggerUtil;

import javafx.application.Platform;

public class CreatePersonCategoryFunction extends TFunction<PersonAttributeParam>{
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(CreatePersonCategoryFunction.class);
	
	public static final String NAME = "create_person_category";
	public static final String DESCRIPTION = "Opens the Person Category module to create new classification groups for people or companies. " +
            "Use this to define segments like 'VIP Customer', 'Strategic Partner', 'Supplier', 'Blacklisted', or 'Prospect'. " +
            "It prepares the form for user validation.";

	public CreatePersonCategoryFunction() {
		super(NAME, DESCRIPTION, PersonAttributeParam.class, 
				v ->{
					
					LOGGER.info("Creating Person Category: {}", v);
					
					TedrosAppManager mng = TedrosAppManager.getInstance();
					Platform.runLater(()->{
						try {
							PersonCategory e = new PersonCategory();
							setProperties(v, e);
							CategoryMV mv = new CategoryMV(e);
							mng.loadInModule(CategoryModule.class, mv);
						} catch (Exception e) {
							LOGGER.error(e.getMessage(), e);
						}
					});
							
					return new Response(SUSCESS_MESSAGE);
				});
	}

	private static void setProperties(PersonAttributeParam v, PersonCategory entity) {
		entity.setCode(v.getCode());
		entity.setName(v.getName());
		entity.setDescription(v.getDescription());
	}

}

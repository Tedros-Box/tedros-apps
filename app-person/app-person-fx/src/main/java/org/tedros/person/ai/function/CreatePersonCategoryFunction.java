package org.tedros.person.ai.function;

import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.person.model.PersonCategory;
import org.tedros.person.module.category.CategoryModule;
import org.tedros.person.module.category.model.CategoryMV;

import javafx.application.Platform;

public class CreatePersonCategoryFunction extends TFunction<PersonAttributeParam>{

	public CreatePersonCategoryFunction() {
		super("create_person_category", "create a person category register", PersonAttributeParam.class, 
				v ->{
					TedrosAppManager mng = TedrosAppManager.getInstance();
					Platform.runLater(()->{
						PersonCategory e = new PersonCategory();
						setProperties(v, e);
						CategoryMV mv = new CategoryMV((PersonCategory) e);
						mng.loadInModule(CategoryModule.class, mv);
					});
							
					return new Response("Success");
				});
	}

	private static void setProperties(PersonAttributeParam v, PersonCategory entity) {
		entity.setCode(v.getCode());
		entity.setName(v.getName());
		entity.setDescription(v.getDescription());
	}

}

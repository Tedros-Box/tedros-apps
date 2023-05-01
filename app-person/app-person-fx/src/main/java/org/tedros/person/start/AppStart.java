package org.tedros.person.start;

import org.tedros.core.ITApplication;
import org.tedros.core.annotation.TApplication;
import org.tedros.core.annotation.TModule;
import org.tedros.core.annotation.TResourceBundle;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.person.PersonKeys;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.module.category.CategoryModule;
import org.tedros.person.module.company.LegalPersonModule;
import org.tedros.person.module.customer.CustomerModule;
import org.tedros.person.module.individual.IndividualModule;
import org.tedros.person.resource.AppResource;

/**
 * The app start class.
 * 
 * @author Davis Gordon
 * */
@TApplication(name=PersonKeys.APP_PERSON, 
universalUniqueIdentifier=TConstant.UUI,
module = {	
	@TModule(type=IndividualModule.class,
		name=PersonKeys.MODULE_NATURAL_PERSON, 
		menu=PersonKeys.MENU_PERSON,
		description=PersonKeys.MODULE_DESC_NATURAL_PERSON,
		icon=TConstant.ICONS_FOLDER + "person.png", 
		menuIcon=TConstant.ICONS_FOLDER + "person_menu.png"),
	@TModule(type=LegalPersonModule.class,
		name=PersonKeys.MODULE_LEGAL_PERSON, 
		menu=PersonKeys.MENU_PERSON, 
		description=PersonKeys.MODULE_DESC_LEGAL_PERSON,
		icon=TConstant.ICONS_FOLDER + "legal_person.png", 
		menuIcon=TConstant.ICONS_FOLDER + "legal_person_menu.png"),
	@TModule(type=CustomerModule.class,
		name=PersonKeys.MODULE_CUSTOMER, 
		menu=PersonKeys.MENU_PERSON, 
		description=PersonKeys.MODULE_DESC_CUSTOMER,
		icon=TConstant.ICONS_FOLDER + "customer.png", 
		menuIcon=TConstant.ICONS_FOLDER + "customer_menu.png"),
	@TModule(type=CategoryModule.class,
		name=PersonKeys.MODULE_PERSON_CATEGORIES, 
		menu=PersonKeys.MENU_PERSON, 
		description=PersonKeys.MODULE_DESC_PERSON_CATEGORIES,
		icon=TConstant.ICONS_FOLDER + "person_category.png", 
		menuIcon=TConstant.ICONS_FOLDER + "person_category_menu.png")

}, packageName = "org.tedros.person")
@TResourceBundle(resourceName={"TPerson"})
@TSecurity(id=DomainApp.MNEMONIC, appName = PersonKeys.APP_PERSON, 
allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		AppResource.createResource();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	
}

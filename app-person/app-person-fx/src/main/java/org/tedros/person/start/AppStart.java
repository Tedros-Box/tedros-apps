package org.tedros.person.start;

import org.tedros.core.ITApplication;
import org.tedros.core.annotation.TApplication;
import org.tedros.core.annotation.TModule;
import org.tedros.core.annotation.TResourceBundle;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.person.PersonKeys;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.module.individual.IndividualModule;
import org.tedros.person.module.legal.LegalPersonModule;
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
		description=PersonKeys.MODULE_DESC_NATURAL_PERSON),
	@TModule(type=LegalPersonModule.class,
		name=PersonKeys.MODULE_LEGAL_PERSON, 
		menu=PersonKeys.MENU_PERSON, 
		description=PersonKeys.MODULE_DESC_LEGAL_PERSON)

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

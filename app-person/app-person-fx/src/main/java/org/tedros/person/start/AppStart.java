package com.tedros.person.start;

import com.tedros.core.ITApplication;
import com.tedros.core.annotation.TApplication;
import com.tedros.core.annotation.TModule;
import com.tedros.core.annotation.TResourceBundle;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.person.PersonKeys;
import com.tedros.person.domain.DomainApp;
import com.tedros.person.module.legal.LegalPersonModule;
import com.tedros.person.module.natural.NaturalPersonModule;
import com.tedros.person.module.report.ReportModule;
import com.tedros.person.resource.AppResource;

/**
 * The app start class.
 * 
 * @author Davis Gordon
 * */
@TApplication(name=PersonKeys.APP_PERSON, 
universalUniqueIdentifier=TConstant.UUI,
module = {	
	@TModule(type=NaturalPersonModule.class,
		name=PersonKeys.MODULE_NATURAL_PERSON, 
		menu=PersonKeys.MENU_PERSON,
		description=PersonKeys.MODULE_DESC_NATURAL_PERSON),
	@TModule(type=LegalPersonModule.class,
		name=PersonKeys.MODULE_LEGAL_PERSON, 
		menu=PersonKeys.MENU_PERSON, 
		description=PersonKeys.MODULE_DESC_LEGAL_PERSON),
	@TModule(type=ReportModule.class,
		name=PersonKeys.MODULE_REPORTS, 
		menu=PersonKeys.MENU_PERSON, 
		description=PersonKeys.MODULE_DESC_REPORTS)

}, packageName = "com.tedros.person")
@TResourceBundle(resourceName={"TPerson"})
@TSecurity(id=DomainApp.MNEMONIC, appName = PersonKeys.APP_PERSON, 
allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		AppResource.createResource();
	}
	
	
}

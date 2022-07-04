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
		description="#{module.rep.desc}"),
	@TModule(type=LegalPersonModule.class,
		name=PersonKeys.MODULE_LEGAL_PERSON, 
		menu=PersonKeys.MENU_PERSON, 
		/*icon=ProdutoIconImageView.class, menuIcon=ProdutoMenuIconImageView.class,*/
		description="#{module.prod.desc}")

}, packageName = "com.tedros.person")
@TResourceBundle(resourceName={"TPerson"})
@TSecurity(id=DomainApp.MNEMONIC, appName = PersonKeys.APP_PERSON, 
allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		// TODO Auto-generated method stub 
		
	}
	
	
}

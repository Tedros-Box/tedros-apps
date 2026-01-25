package org.tedros.it.tools.start;

import org.tedros.core.ITApplication;
import org.tedros.core.annotation.TApplication;
import org.tedros.core.annotation.TModule;
import org.tedros.core.annotation.TResourceBundle;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.module.evidence.JobEvidenceModule;
import org.tedros.it.tools.module.redmine.RedmineModule;
import org.tedros.it.tools.resource.AppResource;

/**
 * The app start class.
 * 
 * @author Davis Dun
 * */
@TApplication(name=ItToolsKey.APP_ITSUPPORT, 
	module = {	
		@TModule(type=JobEvidenceModule.class, 
			name=ItToolsKey.MODULE_ITSUPPORT_EVIDENCE, 
			menu=ItToolsKey.MENU_ITSUPPORT, 
			description=ItToolsKey.MODULE_ITSUPPORT_EVIDENCE_DESC,
			icon=TConstant.ICONS_FOLDER+"job_evidence.png", 
			menuIcon=TConstant.ICONS_FOLDER+"job_evidence_menu.png"),		
		@TModule(type=RedmineModule.class, 
			name=ItToolsKey.MODULE_ITSUPPORT_REDMINE, 
			menu=ItToolsKey.MENU_ITSUPPORT, 
			description=ItToolsKey.MODULE_ITSUPPORT_REDMINE_DESC,
			icon=TConstant.ICONS_FOLDER+"redmine_tools.png", 
			menuIcon=TConstant.ICONS_FOLDER+"redmine_tools_menu.png")
	}, packageName = "org.tedros.it.tools", 
	universalUniqueIdentifier=TConstant.UUI)
@TResourceBundle(resourceName={"ittools"})
@TSecurity(id=DomainApp.MNEMONIC, 
	appName = ItToolsKey.APP_ITSUPPORT, 
	allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {

	@Override
	public void start() {
		AppResource.createResource();
	}

	@Override
	public void stop() {
		// Executed on exit and logout
	}
	
	
}

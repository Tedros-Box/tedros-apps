package org.tedros.it.tools.start;

import org.tedros.core.ITApplication;
import org.tedros.core.annotation.TApplication;
import org.tedros.core.annotation.TModule;
import org.tedros.core.annotation.TResourceBundle;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.module.employeeactivity.MonitorEmployeeActivityModule;
import org.tedros.it.tools.module.employeeactivity.component.EmployeeActivityTrackerService;
import org.tedros.it.tools.module.evidence.JobEvidenceModule;
import org.tedros.it.tools.module.gmud.GmudModule;
import org.tedros.it.tools.module.governance.GovernanceModule;
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
			icon=TConstant.ICONS_FOLDER+"redmine-icon.png", 
			menuIcon=TConstant.ICONS_FOLDER+"redmine-menu-icon.png"),
		@TModule(type=GmudModule.class, 
			name=ItToolsKey.MODULE_ITSUPPORT_GMUD, 
			menu=ItToolsKey.MENU_ITSUPPORT, 
			description=ItToolsKey.MODULE_ITSUPPORT_GMUD_DESC,
			icon=TConstant.ICONS_FOLDER+"gmud-icon.png", 
			menuIcon=TConstant.ICONS_FOLDER+"gmud-menu-icon.png"),
		@TModule(type=MonitorEmployeeActivityModule.class, 
			name=ItToolsKey.MODULE_ITSUPPORT_EMPLOYEE_ACTIVITY, 
			menu=ItToolsKey.MENU_ITSUPPORT, 
			description=ItToolsKey.MODULE_ITSUPPORT_EMPLOYEE_ACTIVITY_DESC,
			icon=TConstant.ICONS_FOLDER+"monitoring_user_activity.png", 
			menuIcon=TConstant.ICONS_FOLDER+"monitoring_user_activity_menu.png"),
		@TModule(type=GovernanceModule.class,
			name=ItToolsKey.MODULE_GOVERNANCE,
			menu=ItToolsKey.MENU_GOVERNANCE,
			description=ItToolsKey.MODULE_GOVERNANCE_DESC/*,
			icon=TConstant.ICONS_FOLDER+"service_catalog.png",
			menuIcon=TConstant.ICONS_FOLDER+"service_catalog_menu.png"*/)
	}, packageName = "org.tedros.it.tools", 
	universalUniqueIdentifier=TConstant.UUI)
@TResourceBundle(resourceName={"ittools"})
@TSecurity(id=DomainApp.MNEMONIC, 
	appName = ItToolsKey.APP_ITSUPPORT, 
	allowedAccesses=TAuthorizationType.APP_ACCESS)
public class AppStart implements ITApplication {
	
	private static EmployeeActivityTrackerService productivityTrackerService;

	@Override
	public void start() {
		AppResource.createResource();
		// Start the productivity tracker service in the background
		productivityTrackerService = new EmployeeActivityTrackerService();
		productivityTrackerService.startTracking(1, java.util.concurrent.TimeUnit.MINUTES);
		
	}

	@Override
	public void stop() {
		// Executed on exit and logout
		productivityTrackerService.stopTracking();
	}
	
	
}

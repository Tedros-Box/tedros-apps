/**
 * 
 */
package org.tedros.it.tools.module.employeeactivity;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.model.ProductivityActivityDTO;
import org.tedros.it.tools.module.employeeactivity.model.EmployeeActivityMV;

/**
 * 
 */
@TView(items = {
		@TItem(title =  ItToolsKey.VIEW_EMPLOYEE_ACTIVITY_MONITORING, 
				description = ItToolsKey.VIEW_EMPLOYEE_ACTIVITY_MONITORING_DESC, 
		modelView = EmployeeActivityMV.class, model = ProductivityActivityDTO.class)})
@TSecurity(id = DomainApp.MONITOR_EMPLOYEE_ACTIVITY_MODULE_ID, appName = ItToolsKey.APP_ITSUPPORT, 
moduleName = ItToolsKey.MODULE_ITSUPPORT_EMPLOYEE_ACTIVITY, allowedAccesses = TAuthorizationType.MODULE_ACCESS)
public class MonitorEmployeeActivityModule extends TModule {

}

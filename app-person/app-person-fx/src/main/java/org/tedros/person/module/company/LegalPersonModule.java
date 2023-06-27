/**
 * 
 */
package org.tedros.person.module.company;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.person.PersonKeys;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.model.CostCenter;
import org.tedros.person.model.Employee;
import org.tedros.person.model.EmployeeStatus;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.model.LegalStatus;
import org.tedros.person.model.LegalType;
import org.tedros.person.model.StaffType;
import org.tedros.person.module.company.model.CompanyMV;
import org.tedros.person.module.company.model.CompanyStatusMV;
import org.tedros.person.module.company.model.CompanyTypeMV;
import org.tedros.person.module.company.model.CostCenterMV;
import org.tedros.person.module.company.model.EmployeeMV;
import org.tedros.person.module.company.model.EmployeeStatusMV;
import org.tedros.person.module.company.model.StaffTypeMV;
import org.tedros.person.module.report.model.EmployeeReportMV;
import org.tedros.person.module.report.model.LegalPersonReportMV;
import org.tedros.person.report.model.EmployeeReportModel;
import org.tedros.person.report.model.LegalPersonReportModel;

/**
 * @author Davis Gordon
 *
 */
@TView(title=PersonKeys.VIEW_LEGAL_PERSON,
items = {
	@TItem(title=PersonKeys.VIEW_LEGAL_PERSON, description=PersonKeys.VIEW_LEGAL_PERSON_DESC,
	model = LegalPerson.class, modelView=CompanyMV.class),
	@TItem(title=PersonKeys.VIEW_LEGAL_TYPE, description=PersonKeys.VIEW_LEGAL_TYPE_DESC,
	model = LegalType.class, modelView=CompanyTypeMV.class),
	@TItem(title=PersonKeys.VIEW_LEGAL_STATUS, description=PersonKeys.VIEW_LEGAL_STATUS_DESC,
	model = LegalStatus.class, modelView=CompanyStatusMV.class),
	@TItem(title=PersonKeys.VIEW_COST_CENTER, description=PersonKeys.VIEW_COST_CENTER_DESC,
	model = CostCenter.class, modelView=CostCenterMV.class),
	@TItem(title=PersonKeys.VIEW_EMPLOYEES, description=PersonKeys.VIEW_EMPLOYEES_DESC,
	model = Employee.class, modelView=EmployeeMV.class),
	@TItem(title=PersonKeys.VIEW_STAFF_TYPE, description=PersonKeys.VIEW_STAFF_TYPE_DESC,
	model = StaffType.class, modelView=StaffTypeMV.class),
	@TItem(title=PersonKeys.VIEW_EMPLOYEE_STATUS, description=PersonKeys.VIEW_EMPLOYEE_STATUS_DESC,
	model = EmployeeStatus.class, modelView=EmployeeStatusMV.class),
	@TItem(title=PersonKeys.VIEW_REPORT_EMPLOYEES, description=PersonKeys.VIEW_REPORT_EMPLOYEES_DESC,
	model = EmployeeReportModel.class, modelView=EmployeeReportMV.class),
	@TItem(title=PersonKeys.VIEW_REPORT_LEGAL_PERSON, description=PersonKeys.VIEW_REPORT_LEGAL_PERSON_DESC,
	model = LegalPersonReportModel.class, modelView=LegalPersonReportMV.class)
})
@TSecurity(id=DomainApp.LEGAL_PERSON_MODULE_ID, 
appName = PersonKeys.APP_PERSON, 
moduleName = PersonKeys.MODULE_LEGAL_PERSON, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class LegalPersonModule extends TModule {


}

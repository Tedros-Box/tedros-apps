/**
 * 
 */
package org.tedros.person.module.company;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TLoadable;
import org.tedros.core.annotation.TModel;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.presenter.dynamic.view.TDynaGroupView;
import org.tedros.fx.presenter.view.group.TGroupPresenter;
import org.tedros.fx.presenter.view.group.TGroupView;
import org.tedros.fx.presenter.view.group.TViewItem;
import org.tedros.person.PersonKeys;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.model.Employee;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.module.company.model.CompanyMV;
import org.tedros.person.module.company.model.CompanyStatusMV;
import org.tedros.person.module.company.model.EmployeeMV;
import org.tedros.person.module.company.model.EmployeeStatusMV;
import org.tedros.person.module.company.model.CompanyTypeMV;
import org.tedros.person.module.company.model.CostCenterMV;
import org.tedros.person.module.company.model.StaffTypeMV;
import org.tedros.person.module.report.model.EmployeeReportMV;
import org.tedros.person.module.report.model.LegalPersonReportMV;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(id=DomainApp.LEGAL_PERSON_MODULE_ID, 
appName = PersonKeys.APP_PERSON, 
moduleName = PersonKeys.MODULE_LEGAL_PERSON, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
@TLoadable({
	@TModel(modelType = LegalPerson.class, modelViewType=CompanyMV.class, moduleType=LegalPersonModule.class),
	@TModel(modelType = Employee.class, modelViewType=EmployeeMV.class, moduleType=LegalPersonModule.class)
})
public class LegalPersonModule extends TModule {

	/* (non-Javadoc)
	 * @see org.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TGroupView<TGroupPresenter>(this, PersonKeys.VIEW_LEGAL_PERSON, 
				new TViewItem(TDynaGroupView.class, CompanyMV.class, PersonKeys.VIEW_LEGAL_PERSON),
				new TViewItem(TDynaGroupView.class, CompanyTypeMV.class, PersonKeys.VIEW_LEGAL_TYPE),
				new TViewItem(TDynaGroupView.class, CompanyStatusMV.class, PersonKeys.VIEW_LEGAL_STATUS), 
				new TViewItem(TDynaGroupView.class, CostCenterMV.class, PersonKeys.VIEW_COST_CENTER),
				new TViewItem(TDynaGroupView.class, EmployeeMV.class, PersonKeys.VIEW_EMPLOYEES), 
				new TViewItem(TDynaGroupView.class, StaffTypeMV.class, PersonKeys.VIEW_STAFF_TYPE), 
				new TViewItem(TDynaGroupView.class, EmployeeStatusMV.class, PersonKeys.VIEW_EMPLOYEE_STATUS), 
				new TViewItem(TDynaGroupView.class, EmployeeReportMV.class, PersonKeys.VIEW_REPORT_EMPLOYEES),
				new TViewItem(TDynaGroupView.class, LegalPersonReportMV.class, PersonKeys.VIEW_REPORT_LEGAL_PERSON)
				));

	}

}

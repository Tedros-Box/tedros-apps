/**
 * 
 */
package com.tedros.person.module.report;

import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaGroupView;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;
import com.tedros.person.PersonKeys;
import com.tedros.person.domain.DomainApp;
import com.tedros.person.module.report.model.EmployeeReportMV;
import com.tedros.person.module.report.model.LegalPersonReportMV;
import com.tedros.person.module.report.model.NaturalPersonReportMV;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(id=DomainApp.REPORT_MODULE_ID, 
appName = PersonKeys.APP_PERSON, 
moduleName = PersonKeys.MODULE_REPORTS, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class ReportModule extends TModule {

	/* (non-Javadoc)
	 * @see com.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TGroupView<TGroupPresenter>(this, PersonKeys.VIEW_REPORTS, 
				new TViewItem(TDynaGroupView.class, EmployeeReportMV.class, PersonKeys.VIEW_REPORT_EMPLOYEES),
				new TViewItem(TDynaGroupView.class, LegalPersonReportMV.class, PersonKeys.VIEW_REPORT_LEGAL_PERSON), 
				new TViewItem(TDynaGroupView.class, NaturalPersonReportMV.class, PersonKeys.VIEW_REPORT_NATURAL_PERSON)
				));

	}

}

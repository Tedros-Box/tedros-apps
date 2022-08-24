/**
 * 
 */
package org.tedros.person.module.report;

import org.tedros.person.PersonKeys;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.module.report.model.EmployeeReportMV;
import org.tedros.person.module.report.model.LegalPersonReportMV;
import org.tedros.person.module.report.model.NaturalPersonReportMV;

import org.tedros.core.TModule;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.presenter.dynamic.view.TDynaGroupView;
import org.tedros.fx.presenter.view.group.TGroupPresenter;
import org.tedros.fx.presenter.view.group.TGroupView;
import org.tedros.fx.presenter.view.group.TViewItem;

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
	 * @see org.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TGroupView<TGroupPresenter>(this, PersonKeys.VIEW_REPORTS, 
				new TViewItem(TDynaGroupView.class, NaturalPersonReportMV.class, PersonKeys.VIEW_REPORT_NATURAL_PERSON), 
				new TViewItem(TDynaGroupView.class, EmployeeReportMV.class, PersonKeys.VIEW_REPORT_EMPLOYEES),
				new TViewItem(TDynaGroupView.class, LegalPersonReportMV.class, PersonKeys.VIEW_REPORT_LEGAL_PERSON)
				));

	}

}

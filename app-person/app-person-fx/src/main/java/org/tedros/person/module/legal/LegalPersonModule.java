/**
 * 
 */
package org.tedros.person.module.legal;

import org.tedros.person.PersonKeys;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.module.legal.model.EmployeeMV;
import org.tedros.person.module.legal.model.LegalPersonMV;
import org.tedros.person.module.legal.model.LegalTypeMV;
import org.tedros.person.module.legal.model.StaffTypeMV;

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
@TSecurity(id=DomainApp.LEGAL_PERSON_MODULE_ID, 
appName = PersonKeys.APP_PERSON, 
moduleName = PersonKeys.MODULE_LEGAL_PERSON, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class LegalPersonModule extends TModule {

	/* (non-Javadoc)
	 * @see org.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TGroupView<TGroupPresenter>(this, PersonKeys.VIEW_LEGAL_PERSON, 
				new TViewItem(TDynaGroupView.class, LegalPersonMV.class, PersonKeys.VIEW_LEGAL_PERSON),
				new TViewItem(TDynaGroupView.class, LegalTypeMV.class, PersonKeys.VIEW_LEGAL_TYPE), 
				new TViewItem(TDynaGroupView.class, StaffTypeMV.class, PersonKeys.VIEW_STAFF_TYPE), 
				new TViewItem(TDynaGroupView.class, EmployeeMV.class, PersonKeys.VIEW_EMPLOYEES)
				));

	}

}

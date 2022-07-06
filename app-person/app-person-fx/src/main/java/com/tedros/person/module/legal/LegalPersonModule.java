/**
 * 
 */
package com.tedros.person.module.legal;

import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaGroupView;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;
import com.tedros.person.PersonKeys;
import com.tedros.person.domain.DomainApp;
import com.tedros.person.module.legal.model.EmployeeMV;
import com.tedros.person.module.legal.model.LegalPersonMV;
import com.tedros.person.module.legal.model.LegalTypeMV;
import com.tedros.person.module.legal.model.StaffTypeMV;

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
	 * @see com.tedros.core.ITModule#tStart()
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

/**
 * 
 */
package org.tedros.person.module.individual;

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
import org.tedros.person.model.Member;
import org.tedros.person.model.NaturalPerson;
import org.tedros.person.model.Philanthrope;
import org.tedros.person.model.Voluntary;
import org.tedros.person.module.individual.model.IndividualMV;
import org.tedros.person.module.individual.model.IndividualStatusMV;
import org.tedros.person.module.individual.model.IndividualTypeMV;
import org.tedros.person.module.individual.model.MemberMV;
import org.tedros.person.module.individual.model.MemberStatusMV;
import org.tedros.person.module.individual.model.MemberTypeMV;
import org.tedros.person.module.individual.model.PhilanthropeMV;
import org.tedros.person.module.individual.model.PhilanthropeStatusMV;
import org.tedros.person.module.individual.model.PhilanthropeTypeMV;
import org.tedros.person.module.individual.model.VoluntaryMV;
import org.tedros.person.module.individual.model.VoluntaryStatusMV;
import org.tedros.person.module.individual.model.VoluntaryTypeMV;
import org.tedros.person.module.report.model.NaturalPersonReportMV;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(id=DomainApp.NATURAL_PERSON_MODULE_ID, 
appName = PersonKeys.APP_PERSON, 
moduleName = PersonKeys.MODULE_NATURAL_PERSON, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
@TLoadable({
	@TModel(modelType = NaturalPerson.class, 
		modelViewType=IndividualMV.class, 
		moduleType=IndividualModule.class),
	@TModel(modelType = Member.class, 
		modelViewType=MemberMV.class, 
		moduleType=IndividualModule.class),
	@TModel(modelType = Philanthrope.class, 
	modelViewType=PhilanthropeMV.class, 
	moduleType=IndividualModule.class),
	@TModel(modelType = Voluntary.class, 
	modelViewType=VoluntaryMV.class, 
	moduleType=IndividualModule.class)
})
public class IndividualModule extends TModule {

	/* (non-Javadoc)
	 * @see org.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TGroupView<TGroupPresenter>(this, PersonKeys.VIEW_NATURAL_PERSON, 
				new TViewItem(TDynaGroupView.class, IndividualMV.class, PersonKeys.VIEW_NATURAL_PERSON),
				new TViewItem(TDynaGroupView.class, IndividualTypeMV.class, PersonKeys.VIEW_NATURAL_PERSON_TYPE), 
				new TViewItem(TDynaGroupView.class, IndividualStatusMV.class, PersonKeys.VIEW_NATURAL_PERSON_STATUS),
				new TViewItem(TDynaGroupView.class, MemberMV.class, PersonKeys.VIEW_MEMBER), 
				new TViewItem(TDynaGroupView.class, MemberTypeMV.class, PersonKeys.VIEW_MEMBER_TYPE), 
				new TViewItem(TDynaGroupView.class, MemberStatusMV.class, PersonKeys.VIEW_MEMBER_STATUS), 
				new TViewItem(TDynaGroupView.class, PhilanthropeMV.class, PersonKeys.VIEW_PHILANTHROPE), 
				new TViewItem(TDynaGroupView.class, PhilanthropeTypeMV.class, PersonKeys.VIEW_PHILANTHROPE_TYPE), 
				new TViewItem(TDynaGroupView.class, PhilanthropeStatusMV.class, PersonKeys.VIEW_PHILANTHROPE_STATUS), 
				new TViewItem(TDynaGroupView.class, VoluntaryMV.class, PersonKeys.VIEW_VOLUNTARY), 
				new TViewItem(TDynaGroupView.class, VoluntaryTypeMV.class, PersonKeys.VIEW_VOLUNTARY_TYPE), 
				new TViewItem(TDynaGroupView.class, VoluntaryStatusMV.class, PersonKeys.VIEW_VOLUNTARY_STATUS), 
				new TViewItem(TDynaGroupView.class, NaturalPersonReportMV.class, PersonKeys.VIEW_REPORT_NATURAL_PERSON) 
				));
	}
}

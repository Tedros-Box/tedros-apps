/**
 * 
 */
package org.tedros.person.module.individual;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.person.PersonKeys;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.model.Member;
import org.tedros.person.model.MemberStatus;
import org.tedros.person.model.MemberType;
import org.tedros.person.model.NaturalPerson;
import org.tedros.person.model.NaturalStatus;
import org.tedros.person.model.NaturalType;
import org.tedros.person.model.Philanthrope;
import org.tedros.person.model.PhilanthropeStatus;
import org.tedros.person.model.PhilanthropeType;
import org.tedros.person.model.Voluntary;
import org.tedros.person.model.VoluntaryStatus;
import org.tedros.person.model.VoluntaryType;
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
import org.tedros.person.report.model.NaturalPersonReportModel;

/**
 * @author Davis Gordon
 *
 */
@TView(title=PersonKeys.VIEW_NATURAL_PERSON,
items = {
	@TItem(title=PersonKeys.VIEW_NATURAL_PERSON, description=PersonKeys.VIEW_NATURAL_PERSON_DESC,
	model = NaturalPerson.class, modelView=IndividualMV.class),
	@TItem(title=PersonKeys.VIEW_NATURAL_PERSON_TYPE, description=PersonKeys.VIEW_NATURAL_PERSON_TYPE_DESC,
	model = NaturalType.class, modelView=IndividualTypeMV.class),
	@TItem(title=PersonKeys.VIEW_NATURAL_PERSON_STATUS, description=PersonKeys.VIEW_NATURAL_PERSON_STATUS_DESC,
	model = NaturalStatus.class, modelView=IndividualStatusMV.class),
	@TItem(title=PersonKeys.VIEW_MEMBER, description=PersonKeys.VIEW_MEMBER_DESC,
	model = Member.class, modelView=MemberMV.class),
	@TItem(title=PersonKeys.VIEW_MEMBER_TYPE, description=PersonKeys.VIEW_MEMBER_TYPE_DESC,
	model = MemberType.class, modelView=MemberTypeMV.class),
	@TItem(title=PersonKeys.VIEW_MEMBER_STATUS, description=PersonKeys.VIEW_MEMBER_STATU_DESC,
	model = MemberStatus.class, modelView=MemberStatusMV.class),
	@TItem(title=PersonKeys.VIEW_PHILANTHROPE, description=PersonKeys.VIEW_PHILANTHROPE_DESC,
	model = Philanthrope.class, modelView=PhilanthropeMV.class),
	@TItem(title=PersonKeys.VIEW_PHILANTHROPE_TYPE, description=PersonKeys.VIEW_PHILANTHROPE_TYPE_DESC,
	model = PhilanthropeType.class, modelView=PhilanthropeTypeMV.class),
	@TItem(title=PersonKeys.VIEW_PHILANTHROPE_STATUS, description=PersonKeys.VIEW_PHILANTHROPE_STATUS_DESC,
	model = PhilanthropeStatus.class, modelView=PhilanthropeStatusMV.class),
	@TItem(title=PersonKeys.VIEW_VOLUNTARY, description=PersonKeys.VIEW_VOLUNTARY_DESC,
	model = Voluntary.class, modelView=VoluntaryMV.class),
	@TItem(title=PersonKeys.VIEW_VOLUNTARY_TYPE, description=PersonKeys.VIEW_VOLUNTARY_TYPE_DESC,
	model = VoluntaryType.class, modelView=VoluntaryTypeMV.class),
	@TItem(title=PersonKeys.VIEW_VOLUNTARY_STATUS, description=PersonKeys.VIEW_VOLUNTARY_STATUS_DESC,
	model = VoluntaryStatus.class, modelView=VoluntaryStatusMV.class),
	@TItem(title=PersonKeys.VIEW_REPORT_NATURAL_PERSON, description=PersonKeys.VIEW_REPORT_NATURAL_PERSON_DESC,
	model = NaturalPersonReportModel.class, modelView=NaturalPersonReportMV.class)
})
@TSecurity(id=DomainApp.NATURAL_PERSON_MODULE_ID, 
appName = PersonKeys.APP_PERSON, 
moduleName = PersonKeys.MODULE_NATURAL_PERSON, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class IndividualModule extends TModule {

}

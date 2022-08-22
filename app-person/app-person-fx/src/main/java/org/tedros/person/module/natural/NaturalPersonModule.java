/**
 * 
 */
package org.tedros.person.module.natural;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TLoadable;
import org.tedros.core.annotation.TModel;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.person.PersonKeys;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.model.NaturalPerson;
import org.tedros.person.module.natural.model.NaturalPersonMV;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(id=DomainApp.NATURAL_PERSON_MODULE_ID, 
appName = PersonKeys.APP_PERSON, 
moduleName = PersonKeys.MODULE_NATURAL_PERSON, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
@TLoadable({
	@TModel(modelType = NaturalPerson.class, modelViewType=NaturalPersonMV.class, moduleType=NaturalPersonModule.class)
})
public class NaturalPersonModule extends TModule {

	/* (non-Javadoc)
	 * @see org.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TDynaView<NaturalPersonMV>(this, NaturalPersonMV.class));
	}
}

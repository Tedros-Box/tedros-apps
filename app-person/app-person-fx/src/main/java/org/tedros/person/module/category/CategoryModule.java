/**
 * 
 */
package org.tedros.person.module.category;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TLoadable;
import org.tedros.core.annotation.TModel;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.person.PersonKeys;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.model.PersonCategory;
import org.tedros.person.module.category.model.CategoryMV;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(id=DomainApp.PERSON_CATEGORY_MODULE_ID, 
appName = PersonKeys.APP_PERSON, 
moduleName = PersonKeys.MODULE_PERSON_CATEGORIES, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
@TLoadable({
	@TModel(modelType = PersonCategory.class, 
			modelViewType=CategoryMV.class,
			moduleType=CategoryModule.class)
})
public class CategoryModule extends TModule {

	/* (non-Javadoc)
	 * @see org.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TDynaView<CategoryMV>(this, CategoryMV.class));
	}
}

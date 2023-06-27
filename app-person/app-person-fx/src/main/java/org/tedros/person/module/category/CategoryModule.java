/**
 * 
 */
package org.tedros.person.module.category;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.person.PersonKeys;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.model.PersonCategory;
import org.tedros.person.module.category.model.CategoryMV;

/**
 * @author Davis Gordon
 *
 */
@TView(items = {
	@TItem(title=PersonKeys.VIEW_PERSON_CATEGORY, description=PersonKeys.VIEW_PERSON_CATEGORY_DESC,
	model = PersonCategory.class, modelView=CategoryMV.class)
})
@TSecurity(id=DomainApp.PERSON_CATEGORY_MODULE_ID, 
appName = PersonKeys.APP_PERSON, 
moduleName = PersonKeys.MODULE_PERSON_CATEGORIES, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class CategoryModule extends TModule {

}

/**
 * 
 */
package org.tedros.extension.module.country;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.extension.LocatKey;
import org.tedros.extension.domain.DomainApp;
import org.tedros.extension.model.Country;
import org.tedros.extension.module.country.model.CountryMV;

/**
 * @author Davis Gordon
 *
 */
@TView(items = { 
		@TItem(title = LocatKey.VIEW_COUNTRY, 
		description=LocatKey.VIEW_COUNTRY_DESC,
		modelView=CountryMV.class, model=Country.class)})
@TSecurity(	id=DomainApp.COUNTRY_MODULE_ID, 
appName = LocatKey.APP_LOCATION_NAME, 
moduleName = LocatKey.MODULE_COUNTRIES, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class CountryModule extends TModule {

}

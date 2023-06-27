/**
 * 
 */
package org.tedros.extension.module.adminArea;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.extension.LocatKey;
import org.tedros.extension.domain.DomainApp;
import org.tedros.extension.model.AdminArea;
import org.tedros.extension.module.adminArea.model.AdminAreaMV;

/**
 * @author Davis Gordon
 *
 */
@TView(items = { 
	@TItem(title = LocatKey.VIEW_ADMIN_AREA, 
	description=LocatKey.VIEW_ADMIN_AREA_DESC,
	modelView=AdminAreaMV.class, model=AdminArea.class)})
@TSecurity(	id=DomainApp.ADMIN_AREA_MODULE_ID, 
appName = LocatKey.APP_LOCATION_NAME, 
moduleName = LocatKey.MODULE_ADMIN_AREA, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class AdminAreaModule extends TModule {


}

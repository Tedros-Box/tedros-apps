/**
 * 
 */
package org.tedros.extension.module.place;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.extension.LocatKey;
import org.tedros.extension.domain.DomainApp;
import org.tedros.extension.model.Place;
import org.tedros.extension.model.PlaceType;
import org.tedros.extension.model.StreetType;
import org.tedros.extension.model.StreetTypeMV;
import org.tedros.extension.module.place.model.MapSettingMV;
import org.tedros.extension.module.place.model.MapSettingModel;
import org.tedros.extension.module.place.model.PlaceMV;
import org.tedros.extension.module.place.model.PlaceTypeMV;
import org.tedros.extension.module.report.model.PlaceReportMV;
import org.tedros.extension.report.model.PlaceReportModel;

/**
 * @author Davis Gordon
 *
 */
@TView(title=LocatKey.VIEW_PLACE_STREET_TYPE,
	items = { 
		@TItem(title = LocatKey.VIEW_PLACE, 
		description=LocatKey.VIEW_PLACE_DESC,
		modelView=PlaceMV.class, model=Place.class),
		@TItem(title = LocatKey.VIEW_PLACE_TYPE, 
		description=LocatKey.VIEW_PLACE_TYPE_DESC,
		modelView=PlaceTypeMV.class, model=PlaceType.class),
		@TItem(title = LocatKey.VIEW_STREET_TYPE, 
		description=LocatKey.VIEW_PLACE_STREET_TYPE_DESC,
		modelView=StreetTypeMV.class, model=StreetType.class),
		@TItem(title = LocatKey.VIEW_MAP_SETTING, 
		description=LocatKey.VIEW_MAP_SETTING_DESC,
		modelView=MapSettingMV.class, model=MapSettingModel.class),
		@TItem(title = LocatKey.VIEW_REPO_PLACE, 
		description=LocatKey.VIEW_REPO_PLACE_DESC,
		modelView=PlaceReportMV.class, model=PlaceReportModel.class),
})
@TSecurity(	id=DomainApp.PLACE_MODULE_ID, 
appName = LocatKey.APP_LOCATION_NAME, 
moduleName = LocatKey.MODULE_PLACES, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class PlaceModule extends TModule {


}

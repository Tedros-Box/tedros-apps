/**
 * 
 */
package com.tedros.location.module.place;

import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaGroupView;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;
import com.tedros.location.LocatKey;
import com.tedros.location.domain.DomainApp;
import com.tedros.location.module.address.model.StreetTypeMV;
import com.tedros.location.module.place.model.MapSettingMV;
import com.tedros.location.module.place.model.PlaceMV;
import com.tedros.location.module.place.model.PlaceTypeMV;
import com.tedros.location.module.report.model.PlaceReportMV;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id=DomainApp.PLACE_MODULE_ID, 
appName = LocatKey.APP_LOCATION_NAME, 
moduleName = LocatKey.MODULE_ADMINISTRATIVE, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class PlaceModule extends TModule {

	/* (non-Javadoc)
	 * @see com.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TGroupView<TGroupPresenter>(this, LocatKey.VIEW_PLACE_STREET_TYPE, 
				new TViewItem(TDynaGroupView.class, PlaceMV.class, LocatKey.VIEW_PLACE),
				new TViewItem(TDynaGroupView.class, PlaceTypeMV.class, LocatKey.VIEW_PLACE_TYPE), 
				new TViewItem(TDynaGroupView.class, StreetTypeMV.class, LocatKey.VIEW_STREET_TYPE), 
				new TViewItem(TDynaGroupView.class, MapSettingMV.class, LocatKey.VIEW_MAP_SETTING), 
				new TViewItem(TDynaGroupView.class, PlaceReportMV.class, LocatKey.VIEW_REPO_PLACE)
				));

	}

}

/**
 * 
 */
package org.tedros.location.module.place;

import org.tedros.location.LocatKey;
import org.tedros.location.domain.DomainApp;
import org.tedros.location.module.address.model.StreetTypeMV;
import org.tedros.location.module.place.model.MapSettingMV;
import org.tedros.location.module.place.model.PlaceMV;
import org.tedros.location.module.place.model.PlaceTypeMV;
import org.tedros.location.module.report.model.PlaceReportMV;

import org.tedros.core.TModule;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.presenter.dynamic.view.TDynaGroupView;
import org.tedros.fx.presenter.view.group.TGroupPresenter;
import org.tedros.fx.presenter.view.group.TGroupView;
import org.tedros.fx.presenter.view.group.TViewItem;

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
	 * @see org.tedros.core.ITModule#tStart()
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

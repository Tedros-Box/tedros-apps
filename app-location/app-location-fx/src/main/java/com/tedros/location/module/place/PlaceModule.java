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
@TSecurity(	id=DomainApp.PLACE_MODULE_ID, appName = "#{app.location.name}", 
moduleName = "#{module.administrative}", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class PlaceModule extends TModule {

	/* (non-Javadoc)
	 * @see com.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TGroupView<TGroupPresenter>(this, "#{view.place.street.type}", 
				new TViewItem(TDynaGroupView.class, PlaceMV.class, "#{view.place}"),
				new TViewItem(TDynaGroupView.class, PlaceTypeMV.class, "#{view.place.type}"), 
				new TViewItem(TDynaGroupView.class, StreetTypeMV.class, "#{view.street.type}"), 
				new TViewItem(TDynaGroupView.class, MapSettingMV.class, "#{view.map.setting}"), 
				new TViewItem(TDynaGroupView.class, PlaceReportMV.class, "#{view.repo.place}")
				));

	}

}

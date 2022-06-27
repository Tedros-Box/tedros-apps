/**
 * 
 */
package com.tedros.location.module.report.table;

import com.tedros.core.ITModule;
import com.tedros.fxapi.builder.TReportRowFactoryCallBackBuilder;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.location.module.place.PlaceModule;
import com.tedros.location.module.place.model.PlaceMV;
import com.tedros.location.module.report.model.PlaceItemMV;

/**
 * @author Davis Gordon
 *
 */
public class PlaceRowFactoryBuilder extends TReportRowFactoryCallBackBuilder<PlaceItemMV> {

	@Override
	protected Class<? extends ITModule> getTargetModuleClass() {
		return PlaceModule.class;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class<? extends TModelView> getTargetModelViewClass() {
		return PlaceMV.class;
	}

}

/**
 * 
 */
package com.tedros.location.module.report.table;

import com.tedros.core.ITModule;
import com.tedros.core.model.ITModelView;
import com.tedros.ejb.base.model.ITModel;
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

	@SuppressWarnings("rawtypes")
	@Override
	protected Class<? extends ITModule> getTargetModuleClass(Class<? extends ITModelView> modelViewClass) {
		return PlaceModule.class;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class<? extends TModelView> getTargetModelViewClass(Class<? extends ITModel> modelClass) {
		return PlaceMV.class;
	}

}

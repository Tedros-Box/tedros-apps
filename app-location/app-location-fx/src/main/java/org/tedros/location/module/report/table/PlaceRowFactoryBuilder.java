/**
 * 
 */
package org.tedros.location.module.report.table;

import org.tedros.location.module.place.PlaceModule;
import org.tedros.location.module.place.model.PlaceMV;
import org.tedros.location.module.report.model.PlaceItemMV;

import org.tedros.core.ITModule;
import org.tedros.core.model.ITModelView;
import org.tedros.server.model.ITModel;
import org.tedros.fx.builder.TReportRowFactoryCallBackBuilder;
import org.tedros.fx.presenter.model.TModelView;

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

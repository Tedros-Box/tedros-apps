/**
 * 
 */
package com.tedros.person.module.report.table;

import com.tedros.core.ITModule;
import com.tedros.core.model.ITModelView;
import com.tedros.ejb.base.model.ITModel;
import com.tedros.fxapi.builder.TReportRowFactoryCallBackBuilder;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.person.module.legal.LegalPersonModule;
import com.tedros.person.module.legal.model.EmployeeMV;

/**
 * @author Davis Gordon
 *
 */
public class EmployeeReportRowFactoryBuilder extends TReportRowFactoryCallBackBuilder<EmployeeItemMV> {

	@SuppressWarnings("rawtypes")
	@Override
	protected Class<? extends ITModule> getTargetModuleClass(Class<? extends ITModelView> modelViewClass) {
		return LegalPersonModule.class;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class<? extends TModelView> getTargetModelViewClass(Class<? extends ITModel> modelClass) {
		return EmployeeMV.class;
	}

}

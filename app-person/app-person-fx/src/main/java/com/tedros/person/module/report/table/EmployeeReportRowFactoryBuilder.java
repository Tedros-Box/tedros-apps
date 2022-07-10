/**
 * 
 */
package com.tedros.person.module.report.table;

import com.tedros.core.ITModule;
import com.tedros.fxapi.builder.TReportRowFactoryCallBackBuilder;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.person.module.legal.LegalPersonModule;
import com.tedros.person.module.legal.model.EmployeeMV;

/**
 * @author Davis Gordon
 *
 */
public class EmployeeReportRowFactoryBuilder extends TReportRowFactoryCallBackBuilder<EmployeeItemMV> {

	@Override
	protected Class<? extends ITModule> getTargetModuleClass() {
		return LegalPersonModule.class;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class<? extends TModelView> getTargetModelViewClass() {
		return EmployeeMV.class;
	}

}

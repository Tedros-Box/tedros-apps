/**
 * 
 */
package org.tedros.person.module.report.table;

import org.tedros.person.module.legal.LegalPersonModule;
import org.tedros.person.module.legal.model.EmployeeMV;

import org.tedros.core.ITModule;
import org.tedros.core.model.ITModelView;
import org.tedros.server.model.ITModel;
import org.tedros.fx.builder.TReportRowFactoryCallBackBuilder;
import org.tedros.fx.presenter.model.TModelView;

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

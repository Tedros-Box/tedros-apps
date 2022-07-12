/**
 * 
 */
package com.tedros.person.module.report.table;

import com.tedros.core.ITModule;
import com.tedros.core.model.ITModelView;
import com.tedros.ejb.base.model.ITModel;
import com.tedros.fxapi.builder.TReportRowFactoryCallBackBuilder;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.person.model.Employee;
import com.tedros.person.module.legal.LegalPersonModule;
import com.tedros.person.module.legal.model.EmployeeMV;
import com.tedros.person.module.natural.NaturalPersonModule;
import com.tedros.person.module.natural.model.NaturalPersonMV;

/**
 * @author Davis Gordon
 *
 */
public class NaturalPersonReportRowFactoryBuilder extends TReportRowFactoryCallBackBuilder<NaturalPersonItemMV> {

	@SuppressWarnings("rawtypes")
	@Override
	protected Class<? extends ITModule> getTargetModuleClass(Class<? extends ITModelView> modelViewClass) {
		return (modelViewClass==EmployeeMV.class) 
				? LegalPersonModule.class 
						: NaturalPersonModule.class;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class<? extends TModelView> getTargetModelViewClass(Class<? extends ITModel> modelClass) {
		return (modelClass==Employee.class) 
				? EmployeeMV.class
						: NaturalPersonMV.class;
	}

}

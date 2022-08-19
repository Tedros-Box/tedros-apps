/**
 * 
 */
package org.tedros.person.module.report.table;

import org.tedros.person.model.Employee;
import org.tedros.person.module.legal.LegalPersonModule;
import org.tedros.person.module.legal.model.EmployeeMV;
import org.tedros.person.module.natural.NaturalPersonModule;
import org.tedros.person.module.natural.model.NaturalPersonMV;

import org.tedros.core.ITModule;
import org.tedros.core.model.ITModelView;
import org.tedros.server.model.ITModel;
import org.tedros.fx.builder.TReportRowFactoryCallBackBuilder;
import org.tedros.fx.presenter.model.TModelView;

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

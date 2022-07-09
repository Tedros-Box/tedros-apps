/**
 * 
 */
package com.tedros.person.server.bo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.person.model.Employee;
import com.tedros.person.report.model.EmployeeReportModel;
import com.tedros.person.server.eao.EmployeeEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class EmployeeBO extends TGenericBO<Employee> {

	@Inject
	private EmployeeEAO eao;
	
	@Override
	public ITGenericEAO<Employee> getEao() {
		return eao;
	}

	public List<Employee> filterBy(EmployeeReportModel m){
		return eao.filterBy(m);
	}
	
}

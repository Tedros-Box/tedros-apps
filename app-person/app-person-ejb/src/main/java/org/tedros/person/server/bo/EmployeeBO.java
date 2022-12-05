/**
 * 
 */
package org.tedros.person.server.bo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.person.model.Employee;
import org.tedros.person.report.model.EmployeeReportModel;
import org.tedros.person.server.eao.EmployeeEAO;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;

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

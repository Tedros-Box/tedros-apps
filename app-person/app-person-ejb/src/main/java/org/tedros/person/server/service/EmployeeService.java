/**
 * 
 */
package org.tedros.person.server.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.person.model.Employee;
import org.tedros.person.report.model.EmployeeReportModel;
import org.tedros.person.server.bo.EmployeeBO;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class EmployeeService extends TEjbService<Employee> {

	@Inject
	private EmployeeBO bo;
	
	@Override
	public ITGenericBO<Employee> getBussinesObject() {
		return bo;
	}
	

	public List<Employee> filterBy(EmployeeReportModel m){
		return bo.filterBy(m);
	}

}

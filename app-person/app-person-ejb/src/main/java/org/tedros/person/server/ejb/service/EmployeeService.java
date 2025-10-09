/**
 * 
 */
package org.tedros.person.server.ejb.service;

import java.util.List;

import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import org.tedros.person.model.Employee;
import org.tedros.person.report.model.EmployeeReportModel;
import org.tedros.person.server.cdi.bo.EmployeeBO;
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

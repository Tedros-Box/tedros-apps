/**
 * 
 */
package com.tedros.person.server.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.person.model.Employee;
import com.tedros.person.report.model.EmployeeReportModel;
import com.tedros.person.server.bo.EmployeeBO;

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

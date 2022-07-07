/**
 * 
 */
package com.tedros.person.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.person.model.Employee;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IEmployeeController extends ITSecureEjbController<Employee> {

	static final String JNDI_NAME = "IEmployeeControllerRemote";
		
}

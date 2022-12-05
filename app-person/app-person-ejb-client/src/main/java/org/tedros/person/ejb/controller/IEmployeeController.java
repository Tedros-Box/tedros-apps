/**
 * 
 */
package org.tedros.person.ejb.controller;

import javax.ejb.Remote;

import org.tedros.person.model.Employee;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IEmployeeController extends ITSecureEjbController<Employee> {

	static final String JNDI_NAME = "IEmployeeControllerRemote";
		
}

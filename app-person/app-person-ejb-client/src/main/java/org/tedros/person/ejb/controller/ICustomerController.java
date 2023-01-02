/**
 * 
 */
package org.tedros.person.ejb.controller;

import javax.ejb.Remote;

import org.tedros.person.model.Customer;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis
 *
 */
@Remote
public interface ICustomerController extends ITSecureEjbController<Customer> {

	static final String JNDI_NAME = "ICustomerControllerRemote";
		
}

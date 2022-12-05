/**
 * 
 */
package org.tedros.person.ejb.controller;

import javax.ejb.Remote;

import org.tedros.person.model.Person;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IPersonController extends ITSecureEjbController<Person> {

	static final String JNDI_NAME = "IPersonControllerRemote";
		
}

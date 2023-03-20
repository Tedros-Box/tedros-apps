/**
 * 
 */
package org.tedros.person.ejb.controller;

import javax.ejb.Remote;

import org.tedros.person.model.PersonType;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IPersonTypeController extends ITSecureEjbController<PersonType> {

	static final String JNDI_NAME = "IPersonTypeControllerRemote";
		
}

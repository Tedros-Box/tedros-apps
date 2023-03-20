/**
 * 
 */
package org.tedros.person.ejb.controller;

import javax.ejb.Remote;

import org.tedros.person.model.PersonStatus;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IPersonStatusController extends ITSecureEjbController<PersonStatus> {

	static final String JNDI_NAME = "IPersonStatusControllerRemote";
		
}

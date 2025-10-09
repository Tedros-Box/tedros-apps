/**
 * 
 */
package org.tedros.person.ejb.controller;

import jakarta.ejb.Remote;

import org.tedros.person.model.NaturalPerson;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface INaturalPersonController extends ITSecureEjbController<NaturalPerson> {

	static final String JNDI_NAME = "INaturalPersonControllerRemote";
		
}

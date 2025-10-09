/**
 * 
 */
package org.tedros.person.ejb.controller;

import jakarta.ejb.Remote;

import org.tedros.person.model.LegalPerson;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface ILegalPersonController extends ITSecureEjbController<LegalPerson> {

	static final String JNDI_NAME = "ILegalPersonControllerRemote";
		
}

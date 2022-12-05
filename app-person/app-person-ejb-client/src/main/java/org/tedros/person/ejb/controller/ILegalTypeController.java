/**
 * 
 */
package org.tedros.person.ejb.controller;

import javax.ejb.Remote;

import org.tedros.person.model.LegalType;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface ILegalTypeController extends ITSecureEjbController<LegalType> {

	static final String JNDI_NAME = "ILegalTypeControllerRemote";
		
}

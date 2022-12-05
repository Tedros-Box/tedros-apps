/**
 * 
 */
package org.tedros.services.ejb.controller;

import javax.ejb.Remote;

import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.services.model.Contractor;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IContractorController extends ITSecureEjbController<Contractor> {

	static final String JNDI_NAME = "IContractorControllerRemote";
		
}

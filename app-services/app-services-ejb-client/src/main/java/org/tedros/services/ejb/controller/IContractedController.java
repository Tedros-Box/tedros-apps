/**
 * 
 */
package org.tedros.services.ejb.controller;

import javax.ejb.Remote;

import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.services.model.Contracted;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IContractedController extends ITSecureEjbController<Contracted> {

	static final String JNDI_NAME = "IContractedControllerRemote";
		
}

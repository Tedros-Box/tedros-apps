/**
 * 
 */
package org.tedros.services.ejb.controller;

import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.services.model.Contract;

import jakarta.ejb.Remote;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IContractController extends ITSecureEjbController<Contract> {

	static final String JNDI_NAME = "IContractControllerRemote";
		
}

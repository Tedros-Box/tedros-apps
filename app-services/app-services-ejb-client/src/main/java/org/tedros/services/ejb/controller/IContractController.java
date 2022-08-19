/**
 * 
 */
package org.tedros.services.ejb.controller;

import javax.ejb.Remote;

import org.tedros.services.model.Contract;

import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IContractController extends ITSecureEjbController<Contract> {

	static final String JNDI_NAME = "IContractControllerRemote";
		
}

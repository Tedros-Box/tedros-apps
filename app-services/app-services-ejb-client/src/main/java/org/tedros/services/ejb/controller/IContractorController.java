/**
 * 
 */
package org.tedros.services.ejb.controller;

import javax.ejb.Remote;

import org.tedros.services.model.Contractor;

import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IContractorController extends ITSecureEjbController<Contractor> {

	static final String JNDI_NAME = "IContractorControllerRemote";
		
}

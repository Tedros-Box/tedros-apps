/**
 * 
 */
package org.tedros.services.ejb.controller;

import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.services.model.Service;

import jakarta.ejb.Remote;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IServiceController extends ITSecureEjbController<Service> {

	static final String JNDI_NAME = "IServiceControllerRemote";
		
}

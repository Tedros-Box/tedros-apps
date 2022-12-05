/**
 * 
 */
package org.tedros.services.ejb.controller;

import javax.ejb.Remote;

import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.services.model.Service;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IServiceController extends ITSecureEjbController<Service> {

	static final String JNDI_NAME = "IServiceControllerRemote";
		
}

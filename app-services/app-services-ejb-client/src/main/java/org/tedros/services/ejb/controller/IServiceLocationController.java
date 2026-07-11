/**
 * 
 */
package org.tedros.services.ejb.controller;

import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.services.model.ServiceLocation;

import jakarta.ejb.Remote;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IServiceLocationController extends ITSecureEjbController<ServiceLocation> {

	static final String JNDI_NAME = "IServiceLocationControllerRemote";
		
}

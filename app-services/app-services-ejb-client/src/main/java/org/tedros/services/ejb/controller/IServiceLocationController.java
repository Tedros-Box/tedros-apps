/**
 * 
 */
package org.tedros.services.ejb.controller;

import jakarta.ejb.Remote;

import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.services.model.ServiceLocation;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IServiceLocationController extends ITSecureEjbController<ServiceLocation> {

	static final String JNDI_NAME = "IServiceLocationControllerRemote";
		
}

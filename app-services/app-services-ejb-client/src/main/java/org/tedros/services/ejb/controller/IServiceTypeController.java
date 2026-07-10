/**
 * 
 */
package org.tedros.services.ejb.controller;

import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.services.model.ServiceType;

import jakarta.ejb.Remote;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IServiceTypeController extends ITSecureEjbController<ServiceType> {

	static final String JNDI_NAME = "IServiceTypeControllerRemote";
		
}

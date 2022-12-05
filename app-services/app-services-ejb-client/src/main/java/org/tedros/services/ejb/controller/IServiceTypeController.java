/**
 * 
 */
package org.tedros.services.ejb.controller;

import javax.ejb.Remote;

import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.services.model.ServiceType;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IServiceTypeController extends ITSecureEjbController<ServiceType> {

	static final String JNDI_NAME = "IServiceTypeControllerRemote";
		
}

/**
 * 
 */
package org.tedros.services.ejb.controller;

import javax.ejb.Remote;

import org.tedros.services.model.ServiceLocation;

import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IServiceLocationController extends ITSecureEjbController<ServiceLocation> {

	static final String JNDI_NAME = "IServiceLocationControllerRemote";
		
}

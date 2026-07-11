/**
 * 
 */
package org.tedros.services.ejb.controller;

import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.services.model.Plan;

import jakarta.ejb.Remote;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IPlanController extends ITSecureEjbController<Plan> {

	static final String JNDI_NAME = "IPlanControllerRemote";
		
}

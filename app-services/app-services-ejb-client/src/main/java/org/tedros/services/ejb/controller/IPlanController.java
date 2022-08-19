/**
 * 
 */
package org.tedros.services.ejb.controller;

import javax.ejb.Remote;

import org.tedros.services.model.Plan;

import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IPlanController extends ITSecureEjbController<Plan> {

	static final String JNDI_NAME = "IPlanControllerRemote";
		
}

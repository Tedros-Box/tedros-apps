/**
 * 
 */
package com.tedros.services.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.services.model.Plan;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IPlanController extends ITSecureEjbController<Plan> {

	static final String JNDI_NAME = "IPlanControllerRemote";
		
}

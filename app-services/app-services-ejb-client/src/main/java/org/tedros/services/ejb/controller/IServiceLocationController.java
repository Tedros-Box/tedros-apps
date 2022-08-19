/**
 * 
 */
package com.tedros.services.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.services.model.ServiceLocation;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IServiceLocationController extends ITSecureEjbController<ServiceLocation> {

	static final String JNDI_NAME = "IServiceLocationControllerRemote";
		
}

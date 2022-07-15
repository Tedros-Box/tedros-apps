/**
 * 
 */
package com.tedros.services.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.services.model.Service;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IServiceController extends ITSecureEjbController<Service> {

	static final String JNDI_NAME = "IServiceControllerRemote";
		
}

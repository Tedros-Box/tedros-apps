/**
 * 
 */
package com.tedros.services.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.services.model.ServiceType;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IServiceTypeController extends ITSecureEjbController<ServiceType> {

	static final String JNDI_NAME = "IServiceTypeControllerRemote";
		
}

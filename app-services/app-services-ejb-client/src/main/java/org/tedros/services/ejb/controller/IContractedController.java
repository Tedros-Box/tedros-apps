/**
 * 
 */
package com.tedros.services.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.services.model.Contracted;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IContractedController extends ITSecureEjbController<Contracted> {

	static final String JNDI_NAME = "IContractedControllerRemote";
		
}

/**
 * 
 */
package com.tedros.services.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.services.model.Contractor;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IContractorController extends ITSecureEjbController<Contractor> {

	static final String JNDI_NAME = "IContractorControllerRemote";
		
}

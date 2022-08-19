/**
 * 
 */
package com.tedros.services.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.services.model.Contract;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IContractController extends ITSecureEjbController<Contract> {

	static final String JNDI_NAME = "IContractControllerRemote";
		
}

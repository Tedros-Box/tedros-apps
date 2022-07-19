/**
 * 
 */
package com.tedros.services.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.services.model.ContractualAgreement;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IContractualAgreementController extends ITSecureEjbController<ContractualAgreement> {

	static final String JNDI_NAME = "IContractualAgreementControllerRemote";
		
}

/**
 * 
 */
package org.tedros.services.ejb.controller;

import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.services.model.ContractualAgreement;

import jakarta.ejb.Remote;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IContractualAgreementController extends ITSecureEjbController<ContractualAgreement> {

	static final String JNDI_NAME = "IContractualAgreementControllerRemote";
		
}

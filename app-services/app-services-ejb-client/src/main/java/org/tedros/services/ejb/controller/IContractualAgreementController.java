/**
 * 
 */
package org.tedros.services.ejb.controller;

import javax.ejb.Remote;

import org.tedros.server.controller.ITSecureEjbController;
import org.tedros.services.model.ContractualAgreement;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IContractualAgreementController extends ITSecureEjbController<ContractualAgreement> {

	static final String JNDI_NAME = "IContractualAgreementControllerRemote";
		
}

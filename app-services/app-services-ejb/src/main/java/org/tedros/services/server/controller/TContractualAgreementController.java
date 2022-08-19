/**
 * 
 */
package com.tedros.services.server.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessPolicie;
import com.tedros.ejb.base.security.TBeanPolicie;
import com.tedros.ejb.base.security.TBeanSecurity;
import com.tedros.ejb.base.security.TSecurityInterceptor;
import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.services.domain.DomainApp;
import com.tedros.services.ejb.controller.IContractualAgreementController;
import com.tedros.services.model.ContractualAgreement;
import com.tedros.services.server.base.service.TServService;

/**
 * @author Davis Gordon
 *
 */
@TSecurityInterceptor
@Stateless(name="IContractualAgreementController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.CONTRACT_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TContractualAgreementController extends TSecureEjbController<ContractualAgreement> implements IContractualAgreementController, ITSecurity  {

	@EJB
	private TServService<ContractualAgreement> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<ContractualAgreement> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}

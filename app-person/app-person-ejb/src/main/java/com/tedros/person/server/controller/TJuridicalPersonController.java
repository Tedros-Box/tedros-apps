/**
 * 
 */
package com.tedros.person.server.controller;

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
import com.tedros.person.domain.DomainApp;
import com.tedros.person.ejb.controller.IJuridicalPersonController;
import com.tedros.person.model.JuridicalPerson;
import com.tedros.person.server.base.service.TPersonService;

/**
 * @author Davis Gordon
 *
 */
@TSecurityInterceptor
@Stateless(name="IJuridicalPersonController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.JURIDICAL_PERSON_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TJuridicalPersonController extends TSecureEjbController<JuridicalPerson> implements IJuridicalPersonController, ITSecurity  {

	@EJB
	private TPersonService<JuridicalPerson> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<JuridicalPerson> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}

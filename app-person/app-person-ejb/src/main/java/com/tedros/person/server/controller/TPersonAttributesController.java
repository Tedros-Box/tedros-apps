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
import com.tedros.ejb.base.security.TSecurityInterceptor;
import com.tedros.ejb.base.service.ITEjbService;
import com.tedros.person.ejb.controller.IPersonAttributesController;
import com.tedros.person.model.PersonAttributes;
import com.tedros.person.server.base.service.TPersonService;

/**
 * @author Davis Gordon
 *
 */
@TSecurityInterceptor
@Stateless(name="IPersonAttributesController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TPersonAttributesController extends TSecureEjbController<PersonAttributes> 
implements IPersonAttributesController, ITSecurity  {

	@EJB
	private TPersonService<PersonAttributes> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<PersonAttributes> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}

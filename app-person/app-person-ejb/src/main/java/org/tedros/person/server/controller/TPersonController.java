/**
 * 
 */
package org.tedros.person.server.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.person.ejb.controller.IPersonController;
import org.tedros.person.model.Person;
import org.tedros.person.server.base.service.TPersonService;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

/**
 * @author Davis Gordon
 *
 */
@TSecurityInterceptor
@Stateless(name="IPersonController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TPersonController extends TSecureEjbController<Person> implements IPersonController, ITSecurity  {

	@EJB
	private TPersonService<Person> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<Person> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}

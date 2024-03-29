/**
 * 
 */
package org.tedros.person.server.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.person.ejb.controller.IPersonAttributesController;
import org.tedros.person.model.PersonAttributes;
import org.tedros.person.server.ejb.service.TPersonService;
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

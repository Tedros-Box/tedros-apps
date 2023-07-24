/**
 * 
 */
package org.tedros.person.server.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.person.domain.DomainApp;
import org.tedros.person.ejb.controller.ICustomerController;
import org.tedros.person.model.Customer;
import org.tedros.person.server.ejb.service.TPersonService;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

/**
 * The controller bean
 * 
 * @author Davis
 *
 */
@TSecurityInterceptor
@Stateless(name="ICustomerController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.CUSTOMER_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TCustomerController extends TSecureEjbController<Customer> implements ICustomerController, ITSecurity  {

	@EJB
	private TPersonService<Customer> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<Customer> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}

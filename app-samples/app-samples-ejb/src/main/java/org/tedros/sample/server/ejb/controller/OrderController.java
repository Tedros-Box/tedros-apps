/**
 * 
 */
package org.tedros.sample.server.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;
import org.tedros.sample.domain.DomainApp;
import org.tedros.sample.ejb.controller.IOrderController;
import org.tedros.sample.entity.Order;
import org.tedros.sample.server.ejb.service.SmplsService;

/**
 * The controller bean
 * 
 * @author Davis
 *
 */
@TSecurityInterceptor
@Stateless(name="IOrderController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.ORDER_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class OrderController extends TSecureEjbController<Order> implements IOrderController, ITSecurity  {

	@EJB
	private SmplsService<Order> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<Order> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}

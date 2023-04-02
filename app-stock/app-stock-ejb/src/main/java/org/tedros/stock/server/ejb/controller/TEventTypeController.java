/**
 * 
 */
package org.tedros.stock.server.ejb.controller;

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

import org.tedros.stock.domain.DomainApp;
import org.tedros.stock.ejb.controller.IEventTypeController;
import org.tedros.stock.entity.EventType;
import org.tedros.stock.server.ejb.service.STCKService;

/**
 * The controller bean
 * 
 * @author Davis Dun
 *
 */
@TSecurityInterceptor
@Stateless(name="IEventTypeController")
@TBeanSecurity({
	@TBeanPolicie(id = DomainApp.ENTRY_TYPE_FORM_ID, 
	policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS }),
	@TBeanPolicie(id = DomainApp.OUT_TYPE_FORM_ID, 
	policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })
})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TEventTypeController extends TSecureEjbController<EventType> implements IEventTypeController, ITSecurity  {

	@EJB
	private STCKService<EventType> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<EventType> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}

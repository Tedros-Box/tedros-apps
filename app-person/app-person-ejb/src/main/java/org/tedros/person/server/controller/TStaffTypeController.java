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
import com.tedros.person.ejb.controller.IStaffTypeController;
import com.tedros.person.model.StaffType;
import com.tedros.person.server.base.service.TPersonService;

/**
 * @author Davis Gordon
 *
 */
@TSecurityInterceptor
@Stateless(name="IStaffTypeController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.STAFF_TYPE_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TStaffTypeController extends TSecureEjbController<StaffType> implements IStaffTypeController, ITSecurity  {

	@EJB
	private TPersonService<StaffType> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<StaffType> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}

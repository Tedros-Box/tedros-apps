/**
 * 
 */
package com.template.server.controller;

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
import com.template.domain.DomainApp;
import com.template.ejb.controller.I_MyEntity_Controller;
import com.template.entity._MyEntity_;
import com.template.server.base.service.TMP_Service;

/**
 * The controller bean
 * 
 * @author myname
 *
 */
@TSecurityInterceptor
@Stateless(name="I_MyEntity_Controller")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.MY_ENTITY__FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class T_MyEntity_Controller extends TSecureEjbController<_MyEntity_> implements I_MyEntity_Controller, ITSecurity  {

	@EJB
	private TMP_Service<_MyEntity_> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<_MyEntity_> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}

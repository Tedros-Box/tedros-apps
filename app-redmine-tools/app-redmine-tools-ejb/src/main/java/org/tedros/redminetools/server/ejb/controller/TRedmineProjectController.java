/**
 * 
 */
package org.tedros.redminetools.server.ejb.controller;

import org.tedros.redminetools.domain.DomainApp;
import org.tedros.redminetools.ejb.controller.IRedmineProjectController;
import org.tedros.redminetools.model.TProject;
import org.tedros.redminetools.server.ejb.service.RedmineProjectService;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

/**
 * The controller bean
 * 
 * @author Davis Dun
 *
 */
@TSecurityInterceptor
@Stateless(name="IRedmineProjectController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.REDMINE_PROJECT_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TRedmineProjectController extends TSecureEjbController<TProject> implements IRedmineProjectController, ITSecurity  {

	@EJB
	private RedmineProjectService serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<TProject> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}

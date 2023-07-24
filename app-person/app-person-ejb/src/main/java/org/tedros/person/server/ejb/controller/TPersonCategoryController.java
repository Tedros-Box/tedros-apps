/**
 * 
 */
package org.tedros.person.server.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.person.domain.DomainApp;
import org.tedros.person.ejb.controller.IPersonCategoryController;
import org.tedros.person.model.PersonCategory;
import org.tedros.person.server.ejb.service.PersonCategoryService;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

/**
 * @author Davis Gordon
 *
 */
@TSecurityInterceptor
@Stateless(name="IPersonCategoryController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.PERSON_CATEGORY_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TPersonCategoryController extends TSecureEjbController<PersonCategory> 
implements IPersonCategoryController, ITSecurity  {

	@EJB
	private PersonCategoryService serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<PersonCategory> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
}

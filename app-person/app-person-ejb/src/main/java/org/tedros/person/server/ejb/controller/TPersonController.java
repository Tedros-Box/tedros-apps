/**
 * 
 */
package org.tedros.person.server.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.extension.model.Document;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.ejb.controller.IPersonController;
import org.tedros.person.model.Person;
import org.tedros.person.server.ejb.service.TPersonService;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

/**
 * @author Davis Gordon
 *
 */
@TSecurityInterceptor
@Stateless(name="IPersonController")
@TBeanSecurity({
	@TBeanPolicie(id = DomainApp.CUSTOMER_FORM_ID, 
	policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS }),
	@TBeanPolicie(id = DomainApp.EMPLOYEE_FORM_ID, 
	policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS }),
	@TBeanPolicie(id = DomainApp.LEGAL_PERSON_FORM_ID, 
	policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS }),
	@TBeanPolicie(id = DomainApp.NATURAL_PERSON_FORM_ID, 
	policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS }),
	@TBeanPolicie(id = DomainApp.CLIENT_COMPANY_FORM_ID, 
	policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS }),
	@TBeanPolicie(id = DomainApp.MEMBER_FORM_ID, 
	policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS }),
	@TBeanPolicie(id = DomainApp.VOLUNTARY_FORM_ID, 
	policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS }),
	@TBeanPolicie(id = DomainApp.PHILANTHROPE_FORM_ID, 
	policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })
	})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TPersonController<E extends Person> extends TSecureEjbController<E> implements IPersonController<E>, ITSecurity  {

	@EJB
	private TPersonService<E> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<E> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	/* (non-Javadoc)
	 * @see org.tedros.server.ejb.controller.TSecureEjbController#save(org.tedros.server.security.TAccessToken, org.tedros.server.entity.ITEntity)
	 */
	@Override
	public  TResult<E> save(TAccessToken token, E entity) {
		try{
			E e = (E) getService().save(entity);
			e = getService().findById(e);
			boolean f = false;
			if(e.getDocuments()!=null)
				for(Document d : e.getDocuments()) {
					if(d.getIntegratedEntityId()==null) {
						d.setIntegratedEntityId(e.getId());
						f=true;
					}
				}
			if(f)
				e = (E) getService().save(e);
			
			return new TResult<>(TState.SUCCESS, e);
		}catch(Exception e){
			return processException(token, entity, e);
		}
	}

}

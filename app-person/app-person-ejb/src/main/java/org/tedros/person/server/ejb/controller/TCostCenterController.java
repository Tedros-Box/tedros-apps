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
import org.tedros.person.ejb.controller.ICostCenterController;
import org.tedros.person.model.CostCenter;
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
 * The controller bean
 * 
 * @author Davis Dun
 *
 */
@TSecurityInterceptor
@Stateless(name="ICostCenterController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.COST_CENTER_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TCostCenterController extends TSecureEjbController<CostCenter> implements ICostCenterController, ITSecurity  {

	@EJB
	private TPersonService<CostCenter> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<CostCenter> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
	
	public  TResult<CostCenter> save(TAccessToken token, CostCenter entity) {
		try{
			CostCenter e = getService().save(entity);
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
				e = getService().save(e);
			
			return new TResult<>(TState.SUCCESS, e);
		}catch(Exception e){
			return processException(token, entity, e);
		}
	}
}

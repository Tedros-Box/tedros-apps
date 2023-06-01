/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.tedros.extension.server.ejb.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.extension.domain.DomainApp;
import org.tedros.extension.ejb.controller.IAdminAreaController;
import org.tedros.extension.model.AdminArea;
import org.tedros.extension.model.Country;
import org.tedros.extension.server.ejb.service.AdminAreaService;
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
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@TSecurityInterceptor
@Stateless(name="IAdminAreaController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.ADMIN_AREA_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class AdminAreaController extends TSecureEjbController<AdminArea> 
implements IAdminAreaController, ITSecurity {
	
	@EJB
	private AdminAreaService serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<AdminArea> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}

	@Override
	public TResult<List<AdminArea>> filter(TAccessToken token, Country country) {
		try {
			return new TResult<>(TState.SUCCESS, serv.filter(country));
		} catch (Exception e) {
			return super.processException(token, null, e);
		}
	}
		

}

package org.tedros.it.tools.ejb.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.ejb.service.ServiceCatalogService;
import org.tedros.it.tools.entity.ServiceCatalog;
import org.tedros.it.tools.model.ServiceCatalogReportItemModel;
import org.tedros.it.tools.model.ServiceCatalogReportModel;
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

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

@TSecurityInterceptor
@TBeanSecurity({@TBeanPolicie(id = DomainApp.GOVERNANCE_SERVICE_REPORT_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@Stateless(name="IServiceCatalogReportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ServiceCatalogReportController extends TSecureEjbController<ServiceCatalog> 
implements IServiceCatalogReportController, ITSecurity {

	@EJB
	private ServiceCatalogService serv;
	
	@EJB
	private ITSecurityController security;
	
	@Override
	public ITSecurityController getSecurityController() {
		return security;
	}
	
	@Override
	protected ServiceCatalogService getService() {
		return serv;
	}
	
	@Override
	public TResult<ServiceCatalogReportModel> process(TAccessToken token, ServiceCatalogReportModel model) {		
		try {
			List<ServiceCatalog> lst = serv.search(model.getName());
			if(ObjectUtils.isNotEmpty(lst)) {
				List<ServiceCatalogReportItemModel> items = lst.stream()
						.map(e->{								
							return new ServiceCatalogReportItemModel(e);
						})
						.toList();
				model.setResult(new ArrayList<>(items));
			}
			
			return new TResult<>(TState.SUCCESS, model);
		}catch (Exception e) {
			return super.processException(token, null, e);
		}
	}	

}

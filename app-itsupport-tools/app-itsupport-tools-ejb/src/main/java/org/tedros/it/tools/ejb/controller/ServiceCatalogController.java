package org.tedros.it.tools.ejb.controller;

import java.util.List;

import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.ejb.service.ServiceCatalogService;
import org.tedros.it.tools.entity.ServiceCatalog;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

/**
 * Controller for ServiceCatalog.
 * Provides standard CRUD and a custom search method with security enforcement.
 *
 * @author Davis Gordon
 */
@TSecurityInterceptor
@Stateless(name = "IServiceCatalogController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.GOVERNANCE_SERVICE_CATALOG_FORM_ID,
        policie = {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS})})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ServiceCatalogController extends TSecureEjbController<ServiceCatalog>
        implements IServiceCatalogController, ITSecurity {

    @EJB
    private ServiceCatalogService serv;

    @EJB
    private ITSecurityController securityController;

    @Override
    public ITEjbService<ServiceCatalog> getService() {
        return serv;
    }

    @Override
    public ITSecurityController getSecurityController() {
        return securityController;
    }

    @Override
    public TResult<List<ServiceCatalog>> search(TAccessToken token, String name) {
        try {
            List<ServiceCatalog> list = serv.search(name);
            return new TResult<>(TResult.TState.SUCCESS, list);
        } catch (Exception e) {
            return super.processException(token, null, e);
        }
    }
}

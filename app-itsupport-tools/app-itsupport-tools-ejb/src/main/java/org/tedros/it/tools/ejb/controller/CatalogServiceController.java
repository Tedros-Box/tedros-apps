package org.tedros.it.tools.ejb.controller;

import java.util.List;

import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.ejb.service.CatalogServiceService;
import org.tedros.it.tools.entity.CatalogService;
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
 * Controller for CatalogService.
 * Provides standard CRUD and a custom search method with security enforcement.
 *
 * @author Davis Gordon
 */
@TSecurityInterceptor
@Stateless(name = "ICatalogServiceController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.GOVERNANCE_SERVICE_CATALOG_FORM_ID,
        policie = {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS})})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class CatalogServiceController extends TSecureEjbController<CatalogService>
        implements ICatalogServiceController, ITSecurity {

    @EJB
    private CatalogServiceService serv;

    @EJB
    private ITSecurityController securityController;

    @Override
    public ITEjbService<CatalogService> getService() {
        return serv;
    }

    @Override
    public ITSecurityController getSecurityController() {
        return securityController;
    }

    @Override
    public TResult<List<CatalogService>> search(TAccessToken token, String name, Integer number, Long groupId) {
        try {
            List<CatalogService> list = serv.search(name, number, groupId);
            return new TResult<>(TResult.TState.SUCCESS, list);
        } catch (Exception e) {
            return super.processException(token, null, e);
        }
    }
}

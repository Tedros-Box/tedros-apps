package org.tedros.it.tools.ejb.controller;

import java.util.List;

import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.ejb.service.ServiceVariantService;
import org.tedros.it.tools.entity.ServiceVariant;
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
 * Controller for ServiceVariant.
 * Provides standard CRUD and a custom search method with security enforcement.
 *
 * @author Davis Gordon
 */
@TSecurityInterceptor
@Stateless(name = "IServiceVariantController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.GOVERNANCE_CATALOG_SERVICE_FORM_ID,
        policie = {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS})})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ServiceVariantController extends TSecureEjbController<ServiceVariant>
        implements IServiceVariantController, ITSecurity {

    @EJB
    private ServiceVariantService serv;

    @EJB
    private ITSecurityController securityController;

    @Override
    public ITEjbService<ServiceVariant> getService() {
        return serv;
    }

    @Override
    public ITSecurityController getSecurityController() {
        return securityController;
    }

    @Override
    public TResult<List<ServiceVariant>> search(TAccessToken token, String complexity, Long serviceId) {
        try {
            List<ServiceVariant> list = serv.search(complexity, serviceId);
            return new TResult<>(TResult.TState.SUCCESS, list);
        } catch (Exception e) {
            return super.processException(token, null, e);
        }
    }
}

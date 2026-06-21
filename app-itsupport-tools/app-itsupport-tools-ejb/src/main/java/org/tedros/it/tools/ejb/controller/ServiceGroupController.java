package org.tedros.it.tools.ejb.controller;

import java.util.List;

import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.ejb.service.ServiceGroupService;
import org.tedros.it.tools.entity.ServiceGroup;
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
 * Controller for ServiceGroup.
 * Provides standard CRUD and a custom search method with security enforcement.
 *
 * @author Davis Gordon
 */
@TSecurityInterceptor
@Stateless(name = "IServiceGroupController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.GOVERNANCE_SERVICE_CATALOG_FORM_ID,
        policie = {TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS})})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ServiceGroupController extends TSecureEjbController<ServiceGroup>
        implements IServiceGroupController, ITSecurity {

    @EJB
    private ServiceGroupService serv;

    @EJB
    private ITSecurityController securityController;

    @Override
    public ITEjbService<ServiceGroup> getService() {
        return serv;
    }

    @Override
    public ITSecurityController getSecurityController() {
        return securityController;
    }

    @Override
    public TResult<List<ServiceGroup>> search(TAccessToken token, String name, Long catalogId) {
        try {
            List<ServiceGroup> list = serv.search(name, catalogId);
            return new TResult<>(TResult.TState.SUCCESS, list);
        } catch (Exception e) {
            return super.processException(token, null, e);
        }
    }
}

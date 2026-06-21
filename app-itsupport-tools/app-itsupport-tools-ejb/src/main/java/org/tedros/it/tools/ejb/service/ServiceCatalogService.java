package org.tedros.it.tools.ejb.service;

import java.util.List;

import org.tedros.it.tools.cdi.bo.ServiceCatalogBo;
import org.tedros.it.tools.entity.ServiceCatalog;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

/**
 * EJB service for ServiceCatalog.
 *
 * @author Davis Gordon
 */
@LocalBean
@Stateless(name = "ServiceCatalogService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ServiceCatalogService extends TEjbService<ServiceCatalog> {

    @Inject
    private ServiceCatalogBo bo;

    @Override
    public ITGenericBO<ServiceCatalog> getBussinesObject() {
        return bo;
    }

    public List<ServiceCatalog> search(String name) {
        return bo.search(name);
    }
}

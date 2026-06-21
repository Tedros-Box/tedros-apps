package org.tedros.it.tools.ejb.service;

import java.util.List;

import org.tedros.it.tools.cdi.bo.ServiceVariantBo;
import org.tedros.it.tools.entity.ServiceVariant;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

/**
 * EJB service for ServiceVariant.
 *
 * @author Davis Gordon
 */
@LocalBean
@Stateless(name = "ServiceVariantService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ServiceVariantService extends TEjbService<ServiceVariant> {

    @Inject
    private ServiceVariantBo bo;

    @Override
    public ITGenericBO<ServiceVariant> getBussinesObject() {
        return bo;
    }

    public List<ServiceVariant> search(String complexity, Long serviceId) {
        return bo.search(complexity, serviceId);
    }
}

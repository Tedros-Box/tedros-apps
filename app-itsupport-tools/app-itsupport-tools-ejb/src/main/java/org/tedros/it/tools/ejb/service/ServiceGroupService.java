package org.tedros.it.tools.ejb.service;

import java.util.List;

import org.tedros.it.tools.cdi.bo.ServiceGroupBo;
import org.tedros.it.tools.entity.ServiceGroup;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

/**
 * EJB service for ServiceGroup.
 *
 * @author Davis Gordon
 */
@LocalBean
@Stateless(name = "ServiceGroupService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ServiceGroupService extends TEjbService<ServiceGroup> {

    @Inject
    private ServiceGroupBo bo;

    @Override
    public ITGenericBO<ServiceGroup> getBussinesObject() {
        return bo;
    }

    public List<ServiceGroup> search(String name, Long catalogId) {
        return bo.search(name, catalogId);
    }
}

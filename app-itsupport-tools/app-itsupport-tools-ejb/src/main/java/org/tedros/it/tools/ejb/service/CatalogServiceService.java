package org.tedros.it.tools.ejb.service;

import java.util.List;

import org.tedros.it.tools.cdi.bo.CatalogServiceBo;
import org.tedros.it.tools.entity.CatalogService;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

/**
 * EJB service for CatalogService.
 *
 * @author Davis Gordon
 */
@LocalBean
@Stateless(name = "CatalogServiceService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class CatalogServiceService extends TEjbService<CatalogService> {

    @Inject
    private CatalogServiceBo bo;

    @Override
    public ITGenericBO<CatalogService> getBussinesObject() {
        return bo;
    }

    public List<CatalogService> search(String name, Integer number, Long groupId) {
        return bo.search(name, number, groupId);
    }
}

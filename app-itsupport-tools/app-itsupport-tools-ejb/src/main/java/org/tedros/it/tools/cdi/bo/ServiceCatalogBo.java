package org.tedros.it.tools.cdi.bo;

import java.util.List;

import org.tedros.it.tools.cdi.eao.ServiceCatalogEao;
import org.tedros.it.tools.entity.ServiceCatalog;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

/**
 * Business object for ServiceCatalog.
 * Exposes a custom search method delegating to the specialized EAO.
 *
 * @author Davis Gordon
 */
@RequestScoped
public class ServiceCatalogBo extends TGenericBO<ServiceCatalog> {

    @Inject
    private ServiceCatalogEao eao;

    @Override
    public ITGenericEAO<ServiceCatalog> getEao() {
        return eao;
    }

    public List<ServiceCatalog> search(String name) {
        return eao.search(name);
    }
}

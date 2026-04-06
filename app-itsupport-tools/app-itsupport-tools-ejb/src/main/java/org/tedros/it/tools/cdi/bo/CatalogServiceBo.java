package org.tedros.it.tools.cdi.bo;

import java.util.List;

import org.tedros.it.tools.cdi.eao.CatalogServiceEao;
import org.tedros.it.tools.entity.CatalogService;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

/**
 * Business object for CatalogService.
 * Exposes a custom search method delegating to the specialized EAO.
 *
 * @author Davis Gordon
 */
@RequestScoped
public class CatalogServiceBo extends TGenericBO<CatalogService> {

    @Inject
    private CatalogServiceEao eao;

    @Override
    public ITGenericEAO<CatalogService> getEao() {
        return eao;
    }

    public List<CatalogService> search(String name, Integer number, Long groupId) {
        return eao.search(name, number, groupId);
    }
}

package org.tedros.it.tools.cdi.bo;

import java.util.List;

import org.tedros.it.tools.cdi.eao.ServiceGroupEao;
import org.tedros.it.tools.entity.ServiceGroup;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

/**
 * Business object for ServiceGroup.
 * Exposes a custom search method delegating to the specialized EAO.
 *
 * @author Davis Gordon
 */
@RequestScoped
public class ServiceGroupBo extends TGenericBO<ServiceGroup> {

    @Inject
    private ServiceGroupEao eao;

    @Override
    public ITGenericEAO<ServiceGroup> getEao() {
        return eao;
    }

    public List<ServiceGroup> search(String name, Long catalogId) {
        return eao.search(name, catalogId);
    }
}

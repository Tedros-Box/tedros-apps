package org.tedros.it.tools.cdi.bo;

import java.util.List;

import org.tedros.it.tools.cdi.eao.ServiceVariantEao;
import org.tedros.it.tools.entity.ServiceVariant;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

/**
 * Business object for ServiceVariant.
 * Exposes a custom search method delegating to the specialized EAO.
 *
 * @author Davis Gordon
 */
@RequestScoped
public class ServiceVariantBo extends TGenericBO<ServiceVariant> {

    @Inject
    private ServiceVariantEao eao;

    @Override
    public ITGenericEAO<ServiceVariant> getEao() {
        return eao;
    }

    public List<ServiceVariant> search(String complexity, Long serviceId) {
        return eao.search(complexity, serviceId);
    }
}

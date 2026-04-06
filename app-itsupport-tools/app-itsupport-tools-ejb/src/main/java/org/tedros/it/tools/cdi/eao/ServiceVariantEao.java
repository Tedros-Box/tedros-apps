package org.tedros.it.tools.cdi.eao;

import java.util.List;

import org.tedros.it.tools.entity.ServiceVariant;
import org.tedros.server.cdi.eao.TGenericEAO;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.Query;

/**
 * Specific EAO for ServiceVariant with optional filters by complexity and service.
 *
 * @author Davis Gordon
 */
@RequestScoped
public class ServiceVariantEao extends TGenericEAO<ServiceVariant> {

    @SuppressWarnings("unchecked")
    public List<ServiceVariant> search(String complexity, Long serviceId) {

        StringBuilder sb = new StringBuilder("select distinct v ");
        sb.append("from ServiceVariant v ")
          .append("left join v.service s ")
          .append("where 1=1 ");

        if (complexity != null && !complexity.isBlank())
            sb.append("and lower(v.complexity) like :complexity ");
        if (serviceId != null)
            sb.append("and s.id = :serviceId ");

        sb.append("order by v.variantId asc");

        Query qry = super.getEntityManager().createQuery(sb.toString());

        if (complexity != null && !complexity.isBlank())
            qry.setParameter("complexity", "%" + complexity.toLowerCase() + "%");
        if (serviceId != null)
            qry.setParameter("serviceId", serviceId);

        return qry.getResultList();
    }
}

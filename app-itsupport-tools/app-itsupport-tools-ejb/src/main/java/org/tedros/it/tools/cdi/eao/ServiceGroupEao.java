package org.tedros.it.tools.cdi.eao;

import java.util.List;

import org.tedros.it.tools.entity.ServiceGroup;
import org.tedros.server.cdi.eao.TGenericEAO;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.Query;

/**
 * Specific EAO for ServiceGroup with optional name and catalog-based search.
 *
 * @author Davis Gordon
 */
@RequestScoped
public class ServiceGroupEao extends TGenericEAO<ServiceGroup> {

    @SuppressWarnings("unchecked")
    public List<ServiceGroup> search(String name, Long catalogId) {

        StringBuilder sb = new StringBuilder("select distinct g ");
        sb.append("from ServiceGroup g ")
          .append("left join g.catalog c ")
          .append("where 1=1 ");

        if (name != null && !name.isBlank())
            sb.append("and lower(g.name) like :name ");
        if (catalogId != null)
            sb.append("and c.id = :catalogId ");

        sb.append("order by g.name asc");

        Query qry = super.getEntityManager().createQuery(sb.toString());

        if (name != null && !name.isBlank())
            qry.setParameter("name", "%" + name.toLowerCase() + "%");
        if (catalogId != null)
            qry.setParameter("catalogId", catalogId);

        return qry.getResultList();
    }
}

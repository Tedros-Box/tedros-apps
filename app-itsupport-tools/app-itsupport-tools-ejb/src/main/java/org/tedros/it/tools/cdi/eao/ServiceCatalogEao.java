package org.tedros.it.tools.cdi.eao;

import java.util.Date;
import java.util.List;

import org.tedros.it.tools.entity.CatalogService;
import org.tedros.it.tools.entity.ServiceCatalog;
import org.tedros.server.cdi.eao.TGenericEAO;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.Query;

/**
 * Specific EAO for ServiceCatalog with optional name-based search.
 *
 * @author Davis Gordon
 */
@RequestScoped
public class ServiceCatalogEao extends TGenericEAO<ServiceCatalog> {
	
	@Override
	public void beforePersist(ServiceCatalog entity) throws Exception {
		referenceChilds(entity);
	}
	
	@Override
	public void beforeMerge(ServiceCatalog entity) throws Exception {
		referenceChilds(entity);
	}

	private static void referenceChilds(ServiceCatalog entity) {
		if(entity.getGroups() != null) {
			entity.getGroups().forEach(g -> { 
				g.setCatalog(entity);
				
				if(g.isNew()) {
					g.setInsertDate(new Date());
					g.setCreatedByUserId(entity.getCreatedByUserId());
				}
				
				if(g.getCatalog()!=null)
					g.getServices().forEach(ServiceCatalogEao::referenceChilds);
			});
		}
	}
	
	private static void referenceChilds(CatalogService entity) {
		if(entity.getVariants() != null) {
			entity.getVariants().forEach(v -> {
				v.setService(entity);
				if(v.isNew()) {
					v.setInsertDate(entity.getInsertDate());
					v.setCreatedByUserId(entity.getCreatedByUserId());
				}
			});
		}
	}	

    @SuppressWarnings("unchecked")
    public List<ServiceCatalog> search(String name) {

        StringBuilder sb = new StringBuilder("select distinct c ");
        sb.append("from ServiceCatalog c ")
          .append("where 1=1 ");

        if (name != null && !name.isBlank())
            sb.append("and lower(c.name) like :name ");

        sb.append("order by c.name asc");

        Query qry = super.getEntityManager().createQuery(sb.toString());

        if (name != null && !name.isBlank())
            qry.setParameter("name", "%" + name.toLowerCase() + "%");

        return qry.getResultList();
    }
}

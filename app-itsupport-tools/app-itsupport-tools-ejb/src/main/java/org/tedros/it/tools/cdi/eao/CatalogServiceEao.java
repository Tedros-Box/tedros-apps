package org.tedros.it.tools.cdi.eao;

import java.util.List;

import org.tedros.it.tools.entity.CatalogService;
import org.tedros.server.cdi.eao.TGenericEAO;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.Query;

/**
 * Specific EAO for CatalogService with optional name, number and group-based search.
 *
 * @author Davis Gordon
 */
@RequestScoped
public class CatalogServiceEao extends TGenericEAO<CatalogService> {
	
	@Override
	public void beforePersist(CatalogService entity) throws Exception {
		referenceChilds(entity);
	}
	
	@Override
	public void beforeMerge(CatalogService entity) throws Exception {
		referenceChilds(entity);
	}

	private void referenceChilds(CatalogService entity) {
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
	

	@Override
	public void afterSearch(List<CatalogService> lst) throws Exception {
		lst.forEach(this::loadChilds);
	}
	
	@Override
	public void afterFind(CatalogService entity) throws Exception {
		loadChilds(entity);
	}
	
	@Override
	public void afterFindAll(List<CatalogService> lst) throws Exception {
		lst.forEach(this::loadChilds);
	}
	
	@Override
	public void afterListAll(List<CatalogService> lst) throws Exception {
		lst.forEach(this::loadChilds);
	}
	
	@Override
	public void afterPageAll(List<CatalogService> lst) throws Exception {
		lst.forEach(this::loadChilds);
	}
	
	private void loadChilds(CatalogService entity) {
		if(entity.getGroup() != null) {
			entity.getGroup().getName(); // Force group loading
			entity.getGroup().setCatalog(null); // Avoid circular reference
			entity.getGroup().setServices(null); // Avoid circular reference
		}
	}

    @SuppressWarnings("unchecked")
    public List<CatalogService> search(String name, Integer number, Long groupId) {

        StringBuilder sb = new StringBuilder("select distinct s ");
        sb.append("from CatalogService s ")
          .append("left join s.group g ")
          .append("where 1=1 ");

        if (name != null && !name.isBlank())
            sb.append("and lower(s.name) like :name ");
        if (number != null)
            sb.append("and s.number = :number ");
        if (groupId != null)
            sb.append("and g.id = :groupId ");

        sb.append("order by s.number asc, s.name asc");

        Query qry = super.getEntityManager().createQuery(sb.toString());

        if (name != null && !name.isBlank())
            qry.setParameter("name", "%" + name.toLowerCase() + "%");
        if (number != null)
            qry.setParameter("number", number);
        if (groupId != null)
            qry.setParameter("groupId", groupId);

        return qry.getResultList();
    }
}

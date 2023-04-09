/**
 * 
 */
package org.tedros.stock.server.cdi.eao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.tedros.server.cdi.eao.TGenericEAO;
import org.tedros.stock.entity.Product;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class ProductEAO extends TGenericEAO<Product> {

	
	@SuppressWarnings("unchecked")
	public List<Product> search(String name, String trademark, List<String> codes, String orderBy, String asc){
		
		StringBuilder sb = new StringBuilder("select p ");
		sb.append("from Product p ")
		.append("left join p.images i ")
		.append("where 1=1 ");

		if(name!=null)
			sb.append("and lower(p.name) like :name ");
		if(trademark!=null)
			sb.append("and lower(p.trademark) like :trademark ");
		if(codes!=null)
			sb.append("and p.code in :codes ");
		
		if(orderBy==null) 
			orderBy = "p.name";
		if(asc==null)
			asc = "asc";
		
		sb.append("order by ").append(orderBy);
		sb.append(" ").append(asc);
		
		Query qry = super.getEntityManager().createQuery(sb.toString());

		if(name!=null)
			qry.setParameter("name", "%"+name.toLowerCase()+"%");
		if(trademark!=null)
			qry.setParameter("trademark", "%"+trademark.toLowerCase()+"%");
		if(codes!=null)
			qry.setParameter("codes", codes);
		
		return qry.getResultList();
		
	}
	
}

/**
 * 
 */
package org.tedros.stock.server.cdi.eao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.tedros.person.model.CostCenter;
import org.tedros.person.model.LegalPerson;
import org.tedros.server.cdi.eao.TGenericEAO;
import org.tedros.stock.entity.Product;
import org.tedros.stock.entity.StockItem;
import org.tedros.stock.model.Inventory;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class InventoryEAO extends TGenericEAO<StockItem> {

	
	@SuppressWarnings("unchecked")
	public List<Inventory> calculate(LegalPerson lp, CostCenter cc, Date date, List<Product> products, String orderBy, String asc){
		
		StringBuilder sb = new StringBuilder("select ");
		sb.append("l.name, c.name, p.id, p.code, p.name, ")
		.append("SUM(CASE WHEN (e.dType	= 'ENTRY') ")
		.append("THEN i.amount ELSE i.amount * -1 END) as total ")
		.append("from StockItem i ")
		.append("join i.event e ")
		.append("join e.legalPerson l ")
		.append("join e.costCenter c ")
		.append("join i.product p ")
		.append("where 1=1 ");

		if(lp!=null)
			sb.append("and e.legalPerson = :lp ");
		if(cc!=null)
			sb.append("and e.costCenter = :cc ");
		if(date!=null)
			sb.append("and e.date <= :dt ");
		if(products!=null)
			sb.append("and i.product in :prod ");
		
		sb.append("group by l.name, c.name, p.id, p.code, p.name ");
		
		if(orderBy==null) 
			orderBy = "l.name, c.name, p.name";
		if(asc==null)
			asc = "asc";
		
		sb.append("order by ").append(orderBy);
		sb.append(" ").append(asc);
		
		Query qry = super.getEntityManager().createQuery(sb.toString());

		if(cc!=null)
			qry.setParameter("cc", cc);
		if(date!=null)
			qry.setParameter("dt", date);
		if(products!=null)
			qry.setParameter("prod", products);
		
		List<Inventory> res = new ArrayList<>();
		List<Object[]> l = qry.getResultList();
		l.forEach(arr->{
			String legPer = (String) arr[0];
			String costCen = (String) arr[1];
			Long id = (Long) arr[2];
			String code = (String) arr[3];
			String name = (String) arr[4];
			Double total = (Double) arr[5];
			res.add(new Inventory(legPer, costCen, id, code, name, total));
		});
		
		return res;
		
	}
	
}

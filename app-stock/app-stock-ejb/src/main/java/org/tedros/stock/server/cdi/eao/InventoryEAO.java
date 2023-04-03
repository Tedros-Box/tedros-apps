/**
 * 
 */
package org.tedros.stock.server.cdi.eao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.tedros.server.cdi.eao.TGenericEAO;
import org.tedros.stock.entity.CostCenter;
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
	public List<Inventory> calculate(CostCenter cc, Date date, List<Product> products, String orderBy, String asc){
		
		StringBuilder sb = new StringBuilder("select ");
		sb.append("p.id, p.code, p.name, ")
		.append("SUM(CASE WHEN (e.dType	= 'ENTRY') ")
		.append("THEN i.amount ELSE i.amount * -1 END) as total ")
		.append("from StockItem i ")
		.append("join i.event e ")
		.append("join i.product p ")
		.append("where 1=1 ");

		if(cc!=null)
			sb.append("and e.costCenter = :cc ");
		if(date!=null)
			sb.append("and e.date <= :dt ");
		if(products!=null)
			sb.append("and i.product in :prod ");
		
		sb.append("group by p.id, p.code, p.name ");
		
		if(orderBy==null) 
			orderBy = "p.name";
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
			Long id = (Long) arr[0];
			String code = (String) arr[1];
			String name = (String) arr[2];
			Double total = (Double) arr[3];
			res.add(new Inventory(id, code, name, total));
		});
		
		return res;
		
	}
	
}

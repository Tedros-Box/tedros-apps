/**
 * 
 */
package org.tedros.stock.server.cdi.eao;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.tedros.server.cdi.eao.TGenericEAO;
import org.tedros.stock.entity.CostCenter;
import org.tedros.stock.entity.StockItem;
import org.tedros.stock.model.Inventory;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class InventoryEAO extends TGenericEAO<StockItem> {

	
	public List<Inventory> calculate(CostCenter cc){
		
		StringBuilder sb = new StringBuilder("select ");
		sb.append("p.id, p.code, p.name, ")
		.append("SUM(CASE WHEN (e.id IS NOT NULL) ")
		.append("THEN i.amount ELSE i.amount * -1 END) as total ")
		.append("from StockItem i ")
		.append("left join i.entry e ")
		.append("left join i.out o ")
		.append("left join i.product p ")
		.append("where ")
		.append("e.costCenter = :cc1 ")
		.append("or o.costCenter = :cc2 ")
		.append("group by p.id, p.code, p.name ")
		.append("order by p.name")
		;
		
		Query qry = super.getEntityManager().createQuery(sb.toString());
		qry.setParameter("cc1", cc);
		qry.setParameter("cc2", cc);
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

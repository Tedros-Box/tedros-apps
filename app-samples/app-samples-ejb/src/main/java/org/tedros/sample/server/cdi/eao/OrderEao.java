/**
 * 
 */
package org.tedros.sample.server.cdi.eao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.tedros.person.model.CostCenter;
import org.tedros.person.model.Employee;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.model.Person;
import org.tedros.sample.entity.Order;
import org.tedros.sample.entity.OrderStatus;
import org.tedros.server.cdi.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class OrderEao extends TGenericEAO<Order> {

	@SuppressWarnings("unchecked")
	public Map<Date, Long> count(Date begin, Date end,
			LegalPerson legalPerson, CostCenter costCenter,
			Person customer, Employee seller, OrderStatus status){
		
		StringBuilder sb = new StringBuilder("select FUNCTION('TRUNC', e.date), count(e.id) ");
		sb.append("from Order e ");
		sb.append("left join e.legalPerson lp ");
		sb.append("left join e.costCenter cc ");
		sb.append("left join e.customer c ");
		sb.append("left join e.seller s ");
		sb.append("left join e.status os ");
		sb.append("where 1=1 ");
		
		if(begin!=null) {
			sb.append("and e.date ");
			if(end!=null) 
				sb.append(">= :begin and e.date <= :end ");
			else
				sb.append(">= :begin ");
		}else if(end != null) 
			sb.append("and e.date <= :end ");

		if(legalPerson!=null) 
			sb.append("and lp.id = :lpId ");
		if(costCenter!=null) 
			sb.append("and cc.id = :ccId ");
		if(customer!=null) 
			sb.append("and c.id = :cId ");
		if(seller!=null) 
			sb.append("and s.id = :sId ");
		if(status!=null) 
			sb.append("and os.id = :osId ");
		
		sb.append("group by FUNCTION('TRUNC', e.date) order by FUNCTION('TRUNC', e.date) ");
		
		Query qry = super.getEntityManager().createQuery(sb.toString());
		
		if(begin!=null) {
			if(end!=null) {
				qry.setParameter("begin", begin);
				qry.setParameter("end", end);
			}else
				qry.setParameter("begin", begin);
		}else if(end != null) 
			qry.setParameter("end", end);

		if(legalPerson!=null) 
			qry.setParameter("lpId", legalPerson.getId());
		if(costCenter!=null) 
			qry.setParameter("ccId", costCenter.getId());
		if(customer!=null) 
			qry.setParameter("cId", customer.getId());
		if(seller!=null) 
			qry.setParameter("sId", seller.getId());
		if(status!=null) 
			qry.setParameter("osId", status.getId());
		
		List<Object[]> lst = qry.getResultList();
		Map<Date, Long> res = new HashMap<>();
		if(lst!=null)
			lst.forEach(a->res.put((Date) a[0], (Long) a[1]));
		
		return res;
	}
	
}

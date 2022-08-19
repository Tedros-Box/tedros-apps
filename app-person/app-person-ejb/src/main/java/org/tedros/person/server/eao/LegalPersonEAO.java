/**
 * 
 */
package com.tedros.person.server.eao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.tedros.ejb.base.eao.TGenericEAO;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.person.model.LegalPerson;
import com.tedros.person.report.model.LegalPersonReportModel;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class LegalPersonEAO extends TGenericEAO<LegalPerson> {

	
	
	@SuppressWarnings("unchecked")
	public List<LegalPerson> filterBy(LegalPersonReportModel m){
		
		StringBuffer sbf = new StringBuffer("select distinct e from LegalPerson e ");
		sbf.append("left join e.type t ");
		sbf.append(" where 1=1 ");
		
		if(StringUtils.isNotBlank(m.getName()))
			sbf.append("and lower(e.name) like :name ");
		
		if(StringUtils.isNotBlank(m.getOtherName()))
			sbf.append("and lower(e.otherName) like :otherName ");
		
		if(m.getType()!=null)
			sbf.append("and t.id = :type ");
		
		
		if(m.getStartActivities()!=null) {
			if(m.getStartActivitiesEnd()!=null)
				sbf.append("and e.startActivities >= :sdt and e.startActivities <= :sdte ");
			else
				sbf.append("and e.startActivities = :sdt ");
		}
		if(m.getEndActivities()!=null) {
			if(m.getEndActivitiesEnd()!=null)
				sbf.append("and e.endActivities >= :edt and e.endActivities <= :edte ");
			else
				sbf.append("and e.endActivities = :edt ");
		}
		
		if(StringUtils.isNotBlank(m.getOrderBy())) {
			sbf.append("order by "+m.getOrderBy());
			if(StringUtils.isNotBlank(m.getOrderType()))
				sbf.append(" "+m.getOrderType());
		}
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		
		if(StringUtils.isNotBlank(m.getName()))
			qry.setParameter("name", "%"+m.getName().toLowerCase()+"%");

		if(StringUtils.isNotBlank(m.getOtherName()))
			qry.setParameter("otherName", "%"+m.getOtherName().toLowerCase()+"%");
		
		if(m.getType()!=null)
			qry.setParameter("type", m.getType().getId());
		
		if(m.getStartActivities()!=null) {
			qry.setParameter("sdt", m.getStartActivities());
			if(m.getStartActivitiesEnd()!=null)
				qry.setParameter("sdte", m.getStartActivitiesEnd());
		}
		if(m.getEndActivities()!=null) {
			qry.setParameter("edt", m.getEndActivities());
			if(m.getEndActivitiesEnd()!=null)
				qry.setParameter("edte", m.getEndActivitiesEnd());
		}

		return qry.getResultList();
	}
	
	
}
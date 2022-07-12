/**
 * 
 */
package com.tedros.person.server.eao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.tedros.ejb.base.eao.TGenericEAO;
import com.tedros.person.model.NaturalPerson;
import com.tedros.person.report.model.NaturalPersonReportModel;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class NaturalPersonEAO extends TGenericEAO<NaturalPerson> {

	@SuppressWarnings("unchecked")
	public List<NaturalPerson> filterBy(NaturalPersonReportModel m){
		
		StringBuffer sbf = new StringBuffer("select distinct e from NaturalPerson e ");
		sbf.append("where 1=1 ");
		
		if(StringUtils.isNotBlank(m.getName()))
			sbf.append("and lower(e.name) like :name ");
		
		if(StringUtils.isNotBlank(m.getLastName()))
			sbf.append("and lower(e.lastName) like :lastName ");
		
		if(m.getBirthDate()!=null) {
			if(m.getBirthDateEnd()!=null)
				sbf.append("and e.birthDate >= :bdt and e.birthDate <= :bdte ");
			else
				sbf.append("and e.birthDate = :bdt ");
		}
		
		if(m.getSex()!=null)
			sbf.append("and e.sex = :sex ");
		
		if(m.getGender()!=null)
			sbf.append("and e.gender = :gender ");
		
		if(StringUtils.isNotBlank(m.getOrderBy())) {
			sbf.append("order by "+m.getOrderBy());
			if(StringUtils.isNotBlank(m.getOrderType()))
				sbf.append(" "+m.getOrderType());
		}
		
		Query qry = getEntityManager().createQuery(sbf.toString());
		
		if(StringUtils.isNotBlank(m.getName()))
			qry.setParameter("name", "%"+m.getName().toLowerCase()+"%");

		if(StringUtils.isNotBlank(m.getLastName()))
			qry.setParameter("lastName", "%"+m.getLastName().toLowerCase()+"%");
		
		if(m.getBirthDate()!=null) {
			qry.setParameter("bdt", m.getBirthDate());
			if(m.getBirthDateEnd()!=null) 
				qry.setParameter("bdte", m.getBirthDateEnd());
		}

		if(m.getSex()!=null)
			qry.setParameter("sex", m.getSex());

		if(m.getGender()!=null)
			qry.setParameter("gender", m.getGender());

		return qry.getResultList();
	}
	
}
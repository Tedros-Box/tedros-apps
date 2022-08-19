/**
 * 
 */
package org.tedros.person.server.eao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.tedros.person.model.Employee;
import org.tedros.person.report.model.EmployeeReportModel;

import org.tedros.server.cdi.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class EmployeeEAO extends TGenericEAO<Employee> {

	@SuppressWarnings("unchecked")
	public List<Employee> filterBy(EmployeeReportModel m){
		
		StringBuffer sbf = new StringBuffer("select distinct e from Employee e ");
		sbf.append("left join e.type t ");
		sbf.append("left join e.employer a ");
		sbf.append(" where 1=1 ");
		
		if(StringUtils.isNotBlank(m.getName()))
			sbf.append("and lower(e.name) like :name ");
		
		if(StringUtils.isNotBlank(m.getLastName()))
			sbf.append("and lower(e.lastName) like :lastName ");
		
		if(m.getType()!=null)
			sbf.append("and t.id = :type ");
		
		if(m.getBirthDate()!=null) {
			if(m.getBirthDateEnd()!=null)
				sbf.append("and e.birthDate >= :bdt and e.birthDate <= :bdte ");
			else
				sbf.append("and e.birthDate = :bdt ");
		}
		
		if(m.getHiringDate()!=null) {
			if(m.getHiringDateEnd()!=null)
				sbf.append("and e.hiringDate >= :hdt and e.hiringDate <= :hdte ");
			else
				sbf.append("and e.hiringDate = :hdt ");
		}
		if(m.getResignationDate()!=null) {
			if(m.getResignationDateEnd()!=null)
				sbf.append("and e.resignationDate >= :rdt and e.resignationDate <= :rdte ");
			else
				sbf.append("and e.resignationDate = :rdt ");
		}
		
		if(m.getSex()!=null)
			sbf.append("and e.sex = :sex ");
		
		if(m.getGender()!=null)
			sbf.append("and e.gender = :gender ");
		
		if(m.getEmployer()!=null)
			sbf.append("and a.id = :lpId ");
		
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
		
		if(m.getType()!=null)
			qry.setParameter("type", m.getType().getId());
		
		if(m.getBirthDate()!=null) {
			qry.setParameter("bdt", m.getBirthDate());
			if(m.getBirthDateEnd()!=null) 
				qry.setParameter("bdte", m.getBirthDateEnd());
		}
		
		if(m.getHiringDate()!=null) {
			qry.setParameter("hdt", m.getHiringDate());
			if(m.getHiringDateEnd()!=null)
				qry.setParameter("hdte", m.getHiringDateEnd());
		}
		if(m.getResignationDate()!=null) {
			qry.setParameter("rdt", m.getResignationDate());
			if(m.getResignationDateEnd()!=null)
				qry.setParameter("rdte", m.getResignationDateEnd());
		}

		if(m.getSex()!=null)
			qry.setParameter("sex", m.getSex());

		if(m.getGender()!=null)
			qry.setParameter("gender", m.getGender());

		if(m.getEmployer()!=null)
			qry.setParameter("lpId", m.getEmployer().getId());

		return qry.getResultList();
	}
	
}
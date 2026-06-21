package org.tedros.it.tools.cdi.eao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.tedros.it.tools.entity.JobEvidence;
import org.tedros.person.model.Employee;
import org.tedros.server.cdi.eao.TGenericEAO;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.Query;

@RequestScoped
public class JobEvidenceEao extends TGenericEAO<JobEvidence> {
	
	@SuppressWarnings("unchecked")
	public List<JobEvidence> search(String name, String issueNumber, String issueTitle, Employee employee, Date executionDate, String orderBy, String asc){
		
		StringBuilder sb = new StringBuilder("select distinct p ");
		sb.append("from JobEvidence p ")
		.append("left join p.employee e ")
		.append("left join p.items i ")
		.append("where 1=1 ");

		if(StringUtils.isNotBlank(name))
			sb.append("and lower(p.name) like :name ");
		if(StringUtils.isNotBlank(issueNumber))
			sb.append("and p.issueNumber = :issueNumber ");
		if(StringUtils.isNotBlank(issueTitle))
			sb.append("and lower(p.issueTitle) like :issueTitle ");
		if(employee!=null)
			sb.append("and e.id = :employeeId ");
		if(executionDate!=null) {
			sb.append("and (p.executionDate >= :executionDateBegin ");
			sb.append("and p.executionDate < :executionDateEnd) ");
		}
		
		if(orderBy==null) 
			orderBy = "p.name";
		if(asc==null)
			asc = "asc";
		
		sb.append("order by ").append(orderBy);
		sb.append(" ").append(asc);
		
		Query qry = super.getEntityManager().createQuery(sb.toString());
		
		if(StringUtils.isNotBlank(name))
			qry.setParameter("name", "%"+name.toLowerCase()+"%");
		if(StringUtils.isNotBlank(issueNumber))
			qry.setParameter("issueNumber", issueNumber);
		if(StringUtils.isNotBlank(issueTitle))
			qry.setParameter("issueTitle", "%"+issueTitle.toLowerCase()+"%");
		if(employee!=null)
			qry.setParameter("employeeId", employee.getId());
		if(executionDate!=null) {
			Date executionDateBegin = DateUtils.truncate(executionDate, Calendar.DAY_OF_MONTH);
			Date executionDateEnd = DateUtils.addDays(executionDateBegin, 1);
			qry.setParameter("executionDateBegin", executionDateBegin);
			qry.setParameter("executionDateEnd", executionDateEnd);
		}
		
		return qry.getResultList();
		
	}

}

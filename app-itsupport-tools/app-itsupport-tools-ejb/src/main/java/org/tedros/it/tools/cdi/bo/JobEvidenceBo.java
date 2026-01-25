package org.tedros.it.tools.cdi.bo;

import java.util.Date;
import java.util.List;

import org.tedros.it.tools.cdi.eao.JobEvidenceEao;
import org.tedros.it.tools.entity.JobEvidence;
import org.tedros.person.model.Employee;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class JobEvidenceBo extends TGenericBO<JobEvidence>{

	@Inject
	private JobEvidenceEao eao;
	
	@Override
	public ITGenericEAO<JobEvidence> getEao() {
		return eao;
	}
	
	public List<JobEvidence> search(String name, String issueNumber, String issueTitle, Employee employee, Date executionDate, String orderBy, String asc){
		return eao.search(name, issueNumber, issueTitle, employee, executionDate, orderBy, asc);
	}

}

package org.tedros.it.tools.ejb.service;

import java.util.Date;
import java.util.List;

import org.tedros.it.tools.cdi.bo.JobEvidenceBo;
import org.tedros.it.tools.entity.JobEvidence;
import org.tedros.person.model.Employee;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

@LocalBean
@Stateless(name="JobEvidenceService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class JobEvidenceService  extends TEjbService<JobEvidence>  {

	@Inject
	private JobEvidenceBo bo;
	
	public List<JobEvidence> search(String name, String issueNumber, String issueTitle, Employee employee, Date executionDate, String orderBy, String asc){
		return bo.search(name, issueNumber, issueTitle, employee, executionDate, orderBy, asc);
	}
	
	@Override
	public ITGenericBO<JobEvidence> getBussinesObject() {
		return bo;
	}

}

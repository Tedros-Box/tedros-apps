/**
 * 
 */
package com.tedros.person.server.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;
import com.tedros.person.model.LegalPerson;
import com.tedros.person.report.model.LegalPersonReportModel;
import com.tedros.person.server.bo.LegalPersonBO;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class LegalPersonService extends TEjbService<LegalPerson> {

	@Inject
	private LegalPersonBO bo;
	
	@Override
	public ITGenericBO<LegalPerson> getBussinesObject() {
		return bo;
	}
	

	public List<LegalPerson> filterBy(LegalPersonReportModel m){
		return bo.filterBy(m);
	}

}

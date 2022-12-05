/**
 * 
 */
package org.tedros.person.server.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.person.model.LegalPerson;
import org.tedros.person.report.model.LegalPersonReportModel;
import org.tedros.person.server.bo.LegalPersonBO;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

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

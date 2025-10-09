/**
 * 
 */
package org.tedros.person.server.ejb.service;

import java.util.List;

import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import org.tedros.person.model.NaturalPerson;
import org.tedros.person.report.model.NaturalPersonReportModel;
import org.tedros.person.server.cdi.bo.NaturalPersonBO;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class NaturalPersonService extends TEjbService<NaturalPerson> {

	@Inject
	private NaturalPersonBO bo;
	
	@Override
	public ITGenericBO<NaturalPerson> getBussinesObject() {
		return bo;
	}
	

	public List<NaturalPerson> filterBy(NaturalPersonReportModel m){
		return bo.filterBy(m);
	}

}

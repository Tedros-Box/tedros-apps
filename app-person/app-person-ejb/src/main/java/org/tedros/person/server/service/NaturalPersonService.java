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

import org.tedros.person.model.NaturalPerson;
import org.tedros.person.report.model.NaturalPersonReportModel;
import org.tedros.person.server.bo.NaturalPersonBO;

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

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
import com.tedros.person.model.NaturalPerson;
import com.tedros.person.report.model.NaturalPersonReportModel;
import com.tedros.person.server.bo.NaturalPersonBO;

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

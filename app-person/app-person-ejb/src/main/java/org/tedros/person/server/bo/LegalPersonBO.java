/**
 * 
 */
package com.tedros.person.server.bo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.person.model.LegalPerson;
import com.tedros.person.report.model.LegalPersonReportModel;
import com.tedros.person.server.eao.LegalPersonEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class LegalPersonBO extends TGenericBO<LegalPerson> {

	@Inject
	private LegalPersonEAO eao;
	
	@Override
	public ITGenericEAO<LegalPerson> getEao() {
		return eao;
	}

	public List<LegalPerson> filterBy(LegalPersonReportModel m){
		return eao.filterBy(m);
	}
	
}

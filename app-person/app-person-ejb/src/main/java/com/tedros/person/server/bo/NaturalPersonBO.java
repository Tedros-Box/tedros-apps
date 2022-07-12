/**
 * 
 */
package com.tedros.person.server.bo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.person.model.NaturalPerson;
import com.tedros.person.report.model.NaturalPersonReportModel;
import com.tedros.person.server.eao.NaturalPersonEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class NaturalPersonBO extends TGenericBO<NaturalPerson> {

	@Inject
	private NaturalPersonEAO eao;
	
	@Override
	public ITGenericEAO<NaturalPerson> getEao() {
		return eao;
	}

	public List<NaturalPerson> filterBy(NaturalPersonReportModel m){
		return eao.filterBy(m);
	}
	
}

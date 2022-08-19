/**
 * 
 */
package org.tedros.person.server.bo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.person.model.NaturalPerson;
import org.tedros.person.report.model.NaturalPersonReportModel;
import org.tedros.person.server.eao.NaturalPersonEAO;

import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;

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

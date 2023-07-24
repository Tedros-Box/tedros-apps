/**
 * 
 */
package org.tedros.person.server.cdi.bo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.person.model.LegalPerson;
import org.tedros.person.report.model.LegalPersonReportModel;
import org.tedros.person.server.cdi.eao.LegalPersonEAO;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;

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

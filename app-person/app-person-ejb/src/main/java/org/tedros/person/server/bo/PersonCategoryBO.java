/**
 * 
 */
package org.tedros.person.server.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.person.model.PersonCategory;
import org.tedros.person.server.eao.PersonCategoryEAO;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class PersonCategoryBO extends TGenericBO<PersonCategory> {

	@Inject
	private PersonCategoryEAO eao;
	
	@Override
	public ITGenericEAO<PersonCategory> getEao() {
		return eao;
	}
}

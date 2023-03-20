/**
 * 
 */
package org.tedros.person.server.eao;

import java.util.List;

import javax.enterprise.context.RequestScoped;

import org.tedros.person.model.PersonCategory;
import org.tedros.server.cdi.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class PersonCategoryEAO extends TGenericEAO<PersonCategory> {

	/* (non-Javadoc)
	 * @see org.tedros.server.cdi.eao.TGenericEAO#afterPageAll(java.util.List)
	 */
	@Override
	public void afterPageAll(List<PersonCategory> lst) throws Exception {
		lst.forEach(e->{
			e.getPersons();
		});
	}
	
	
}
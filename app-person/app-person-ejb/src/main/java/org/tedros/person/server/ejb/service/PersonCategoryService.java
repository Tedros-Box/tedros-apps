/**
 * 
 */
package org.tedros.person.server.ejb.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.person.model.PersonCategory;
import org.tedros.person.server.cdi.bo.PersonCategoryBO;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class PersonCategoryService extends TEjbService<PersonCategory> {

	@Inject
	private PersonCategoryBO bo;
	
	@Override
	public ITGenericBO<PersonCategory> getBussinesObject() {
		return bo;
	}
	

}

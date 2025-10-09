/**
 * 
 */
package org.tedros.person.server.ejb.service;

import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

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

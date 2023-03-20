/**
 * 
 */
package org.tedros.person.ejb.controller;

import javax.ejb.Remote;

import org.tedros.person.model.PersonCategory;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IPersonCategoryController extends ITSecureEjbController<PersonCategory> {

	static final String JNDI_NAME = "IPersonCategoryControllerRemote";
		
}

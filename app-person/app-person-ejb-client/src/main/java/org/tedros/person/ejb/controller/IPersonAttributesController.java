/**
 * 
 */
package com.tedros.person.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.person.model.PersonAttributes;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IPersonAttributesController extends ITSecureEjbController<PersonAttributes> {

	static final String JNDI_NAME = "IPersonAttributesControllerRemote";
		
}

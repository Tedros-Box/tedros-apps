/**
 * 
 */
package org.tedros.person.ejb.controller;

import javax.ejb.Remote;

import org.tedros.person.model.PersonAttributes;

import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IPersonAttributesController extends ITSecureEjbController<PersonAttributes> {

	static final String JNDI_NAME = "IPersonAttributesControllerRemote";
		
}

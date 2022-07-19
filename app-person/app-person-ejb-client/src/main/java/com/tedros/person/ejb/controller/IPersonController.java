/**
 * 
 */
package com.tedros.person.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.person.model.Person;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IPersonController extends ITSecureEjbController<Person> {

	static final String JNDI_NAME = "IPersonControllerRemote";
		
}

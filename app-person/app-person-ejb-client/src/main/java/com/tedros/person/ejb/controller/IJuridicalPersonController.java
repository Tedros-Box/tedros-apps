/**
 * 
 */
package com.tedros.person.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.person.model.JuridicalPerson;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IJuridicalPersonController extends ITSecureEjbController<JuridicalPerson> {

	static final String JNDI_NAME = "IJuridicalPersonControllerRemote";
		
}

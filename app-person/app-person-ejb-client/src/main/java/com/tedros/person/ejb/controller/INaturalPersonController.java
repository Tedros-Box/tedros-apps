/**
 * 
 */
package com.tedros.person.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.person.model.NaturalPerson;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface INaturalPersonController extends ITSecureEjbController<NaturalPerson> {

	static final String JNDI_NAME = "INaturalPersonControllerRemote";
		
}

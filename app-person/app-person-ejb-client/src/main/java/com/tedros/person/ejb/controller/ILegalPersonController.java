/**
 * 
 */
package com.tedros.person.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.person.model.LegalPerson;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface ILegalPersonController extends ITSecureEjbController<LegalPerson> {

	static final String JNDI_NAME = "ILegalPersonControllerRemote";
		
}

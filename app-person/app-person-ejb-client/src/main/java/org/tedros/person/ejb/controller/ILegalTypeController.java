/**
 * 
 */
package com.tedros.person.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.person.model.LegalType;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface ILegalTypeController extends ITSecureEjbController<LegalType> {

	static final String JNDI_NAME = "ILegalTypeControllerRemote";
		
}

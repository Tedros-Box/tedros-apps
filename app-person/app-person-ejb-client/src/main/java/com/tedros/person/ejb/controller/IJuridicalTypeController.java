/**
 * 
 */
package com.tedros.person.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITSecureEjbController;
import com.tedros.person.model.JuridicalType;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IJuridicalTypeController extends ITSecureEjbController<JuridicalType> {

	static final String JNDI_NAME = "IJuridicalTypeControllerRemote";
		
}

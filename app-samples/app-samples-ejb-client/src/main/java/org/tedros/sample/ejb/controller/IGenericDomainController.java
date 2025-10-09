/**
 * 
 */
package org.tedros.sample.ejb.controller;

import jakarta.ejb.Remote;

import org.tedros.sample.entity.GenericDomain;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis
 *
 */
@Remote
public interface IGenericDomainController extends ITSecureEjbController<GenericDomain> {

	static final String JNDI_NAME = "IGenericDomainControllerRemote";
		
}

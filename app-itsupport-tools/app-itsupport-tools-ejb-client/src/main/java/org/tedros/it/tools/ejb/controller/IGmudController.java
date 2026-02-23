/**
 * 
 */
package org.tedros.it.tools.ejb.controller;

import org.tedros.it.tools.entity.Gmud;
import org.tedros.server.controller.ITSecureEjbController;

import jakarta.ejb.Remote;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface IGmudController extends ITSecureEjbController<Gmud> {

	static final String JNDI_NAME = "IGmudControllerRemote";
		
}

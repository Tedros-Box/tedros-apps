/**
 * 
 */
package org.tedros.sample.ejb.controller;

import jakarta.ejb.Remote;

import org.tedros.sample.entity.Sale;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis
 *
 */
@Remote
public interface ISaleController extends ITSecureEjbController<Sale> {

	static final String JNDI_NAME = "ISaleControllerRemote";
		
}

/**
 * 
 */
package org.tedros.sample.ejb.controller;

import jakarta.ejb.Remote;

import org.tedros.sample.entity.SaleEventConfig;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis
 *
 */
@Remote
public interface ISaleEventConfigController extends ITSecureEjbController<SaleEventConfig> {

	static final String JNDI_NAME = "ISaleEventConfigControllerRemote";
		
}

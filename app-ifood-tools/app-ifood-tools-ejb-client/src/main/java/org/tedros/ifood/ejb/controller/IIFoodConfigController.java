/**
 * 
 */
package org.tedros.ifood.ejb.controller;

import jakarta.ejb.Remote;

import org.tedros.ifood.entity.IFoodConfig;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface IIFoodConfigController extends ITSecureEjbController<IFoodConfig> {

	static final String JNDI_NAME = "IIFoodConfigControllerRemote";
		
}

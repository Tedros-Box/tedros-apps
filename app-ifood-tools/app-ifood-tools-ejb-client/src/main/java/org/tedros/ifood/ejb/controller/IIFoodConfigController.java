/**
 * 
 */
package org.tedros.ifood.ejb.controller;

import org.tedros.ifood.entity.IFoodConfig;
import org.tedros.server.controller.ITSecureEjbController;

import jakarta.ejb.Remote;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface IIFoodConfigController extends ITSecureEjbController<IFoodConfig> {

	static final String JNDI_NAME = "IIFoodConfigControllerRemote";
		
}

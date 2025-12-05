/**
 * 
 */
package org.tedros.it.tools.ejb.controller;

import jakarta.ejb.Remote;

import org.tedros.it.tools.entity.RedmineConfig;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface IRedmineConfigController extends ITSecureEjbController<RedmineConfig> {

	static final String JNDI_NAME = "IRedmineConfigControllerRemote";
		
}

/**
 * 
 */
package org.tedros.redminetools.ejb.controller;

import jakarta.ejb.Remote;

import org.tedros.redminetools.entity.RedmineConfig;
import org.tedros.server.controller.ITSecureEjbController;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface IRedmineConfigController extends ITSecureEjbController<RedmineConfig> {

	static final String JNDI_NAME = "IRedmineConfigControllerRemote";
		
}

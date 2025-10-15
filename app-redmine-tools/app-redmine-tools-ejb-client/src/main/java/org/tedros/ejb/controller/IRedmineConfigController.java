/**
 * 
 */
package org.tedros.ejb.controller;

import jakarta.ejb.Remote;

import org.tedros.server.controller.ITSecureEjbController;

import org.tedros.entity.RedmineConfig;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface IRedmineConfigController extends ITSecureEjbController<RedmineConfig> {

	static final String JNDI_NAME = "IRedmineConfigControllerRemote";
		
}

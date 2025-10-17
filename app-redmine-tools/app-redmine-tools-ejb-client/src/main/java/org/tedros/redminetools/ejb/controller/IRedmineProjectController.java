/**
 * 
 */
package org.tedros.redminetools.ejb.controller;

import org.tedros.redminetools.model.TProject;
import org.tedros.server.controller.ITSecureEjbController;

import jakarta.ejb.Remote;

/**
 * @author Davis Dun
 *
 */
@Remote
public interface IRedmineProjectController extends ITSecureEjbController<TProject> {

	static final String JNDI_NAME = "IRedmineProjectControllerRemote";
		
}

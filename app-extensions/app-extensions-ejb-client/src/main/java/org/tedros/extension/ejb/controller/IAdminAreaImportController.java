/**
 * 
 */
package org.tedros.extension.ejb.controller;

import javax.ejb.Remote;

import org.tedros.extension.model.AdminArea;
import org.tedros.server.controller.ITEjbImportController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IAdminAreaImportController extends ITEjbImportController<AdminArea> {

	static final String JNDI_NAME = "IAdminAreaImportControllerRemote";
	
}

/**
 * 
 */
package org.tedros.extension.ejb.controller;

import org.tedros.extension.model.AdminArea;
import org.tedros.server.controller.ITEjbImportController;

import jakarta.ejb.Remote;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IAdminAreaImportController extends ITEjbImportController<AdminArea> {

	static final String JNDI_NAME = "IAdminAreaImportControllerRemote";
	
}

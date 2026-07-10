/**
 * 
 */
package org.tedros.extension.ejb.controller;

import org.tedros.extension.model.Country;
import org.tedros.server.controller.ITEjbImportController;

import jakarta.ejb.Remote;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface ICountryImportController extends ITEjbImportController<Country> {

	static final String JNDI_NAME = "ICountryImportControllerRemote";
	
}

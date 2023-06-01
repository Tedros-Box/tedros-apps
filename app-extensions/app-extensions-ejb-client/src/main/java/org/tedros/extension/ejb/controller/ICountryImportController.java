/**
 * 
 */
package org.tedros.extension.ejb.controller;

import javax.ejb.Remote;

import org.tedros.extension.model.Country;
import org.tedros.server.controller.ITEjbImportController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface ICountryImportController extends ITEjbImportController<Country> {

	static final String JNDI_NAME = "ICountryImportControllerRemote";
	
}

/**
 * 
 */
package org.tedros.extension.ejb.controller;

import jakarta.ejb.Remote;

import org.tedros.extension.model.City;
import org.tedros.server.controller.ITEjbImportController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface ICityImportController extends ITEjbImportController<City> {

	static final String JNDI_NAME = "ICityImportControllerRemote";
	
}

/**
 * 
 */
package org.tedros.extension.ejb.controller;

import org.tedros.extension.model.City;
import org.tedros.server.controller.ITEjbImportController;

import jakarta.ejb.Remote;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface ICityImportController extends ITEjbImportController<City> {

	static final String JNDI_NAME = "ICityImportControllerRemote";
	
}

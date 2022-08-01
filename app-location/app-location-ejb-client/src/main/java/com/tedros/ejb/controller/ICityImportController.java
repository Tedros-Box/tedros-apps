/**
 * 
 */
package com.tedros.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITEjbImportController;
import com.tedros.location.model.City;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface ICityImportController extends ITEjbImportController<City> {

	static final String JNDI_NAME = "ICityImportControllerRemote";
	
}

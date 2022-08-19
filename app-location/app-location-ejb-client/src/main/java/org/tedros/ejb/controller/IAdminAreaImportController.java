/**
 * 
 */
package com.tedros.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITEjbImportController;
import com.tedros.location.model.AdminArea;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IAdminAreaImportController extends ITEjbImportController<AdminArea> {

	static final String JNDI_NAME = "IAdminAreaImportControllerRemote";
	
}

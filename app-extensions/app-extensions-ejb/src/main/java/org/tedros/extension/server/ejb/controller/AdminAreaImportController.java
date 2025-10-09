/**
 * 
 */
package org.tedros.extension.server.ejb.controller;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

import org.tedros.extension.ejb.controller.IAdminAreaImportController;
import org.tedros.extension.model.AdminArea;
import org.tedros.extension.server.ejb.service.AdminAreaImportService;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TEjbImportController;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbImportService;

/**
 * @author Davis Gordon
 *
 */
@TSecurityInterceptor
@Stateless(name="IAdminAreaImportController")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class AdminAreaImportController extends TEjbImportController<AdminArea> implements IAdminAreaImportController {

	@EJB
	private AdminAreaImportService serv;
	
	@EJB
	private ITSecurityController security;
	
	@Override
	public ITEjbImportService<AdminArea> getService() {
		return serv;
	}

	@Override
	public ITSecurityController getSecurityController() {
		return security;
	}

	

}

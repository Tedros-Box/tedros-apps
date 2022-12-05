/**
 * 
 */
package org.tedros.server.location.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.ejb.controller.IAdminAreaImportController;
import org.tedros.location.model.AdminArea;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TEjbImportController;
import org.tedros.server.location.service.AdminAreaImportService;
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

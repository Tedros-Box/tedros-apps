/**
 * 
 */
package com.tedros.server.location.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TEjbImportController;
import com.tedros.ejb.base.security.TSecurityInterceptor;
import com.tedros.ejb.base.service.ITEjbImportService;
import com.tedros.ejb.controller.IAdminAreaImportController;
import com.tedros.location.model.AdminArea;
import com.tedros.server.location.service.AdminAreaImportService;

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

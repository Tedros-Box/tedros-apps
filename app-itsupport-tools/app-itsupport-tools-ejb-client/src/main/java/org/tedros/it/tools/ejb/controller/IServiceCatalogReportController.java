package org.tedros.it.tools.ejb.controller;

import org.tedros.it.tools.model.ServiceCatalogReportModel;
import org.tedros.server.controller.ITEjbReportController;

import jakarta.ejb.Remote;

@Remote
public interface IServiceCatalogReportController extends ITEjbReportController<ServiceCatalogReportModel> {

	static final String JNDI_NAME = "IServiceCatalogReportControllerRemote";
	
}

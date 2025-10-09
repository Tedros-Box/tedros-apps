/**
 * 
 */
package org.tedros.extension.ejb.controller;

import jakarta.ejb.Remote;

import org.tedros.extension.report.model.PlaceReportModel;
import org.tedros.server.controller.ITEjbReportController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IPlaceReportController extends ITEjbReportController<PlaceReportModel> {

	static final String JNDI_NAME = "IPlaceReportControllerRemote";
	
}

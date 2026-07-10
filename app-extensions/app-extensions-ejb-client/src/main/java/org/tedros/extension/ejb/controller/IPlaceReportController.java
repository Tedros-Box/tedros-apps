/**
 * 
 */
package org.tedros.extension.ejb.controller;

import org.tedros.extension.report.model.PlaceReportModel;
import org.tedros.server.controller.ITEjbReportController;

import jakarta.ejb.Remote;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IPlaceReportController extends ITEjbReportController<PlaceReportModel> {

	static final String JNDI_NAME = "IPlaceReportControllerRemote";
	
}

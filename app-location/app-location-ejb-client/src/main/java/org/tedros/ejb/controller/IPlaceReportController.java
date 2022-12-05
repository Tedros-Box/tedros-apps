/**
 * 
 */
package org.tedros.ejb.controller;

import javax.ejb.Remote;

import org.tedros.location.report.model.PlaceReportModel;
import org.tedros.server.controller.ITEjbReportController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IPlaceReportController extends ITEjbReportController<PlaceReportModel> {

	static final String JNDI_NAME = "IPlaceReportControllerRemote";
	
}

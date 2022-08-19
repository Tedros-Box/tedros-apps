/**
 * 
 */
package com.tedros.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITEjbReportController;
import com.tedros.location.report.model.PlaceReportModel;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IPlaceReportController extends ITEjbReportController<PlaceReportModel> {

	static final String JNDI_NAME = "IPlaceReportControllerRemote";
	
}

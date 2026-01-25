package org.tedros.it.tools.ejb.controller;

import org.tedros.it.tools.model.JobEvidenceReportModel;
import org.tedros.server.controller.ITEjbReportController;

import jakarta.ejb.Remote;

@Remote
public interface IJobEvidenceReportController extends ITEjbReportController<JobEvidenceReportModel> {

	static final String JNDI_NAME = "IJobEvidenceReportControllerRemote";
	
}

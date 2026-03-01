package org.tedros.it.tools.module.evidence;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.entity.JobEvidence;
import org.tedros.it.tools.model.JobEvidenceReportModel;
import org.tedros.it.tools.model.ProductivityActivityDTO;
import org.tedros.it.tools.module.evidence.model.CaptureEvidenceMV;
import org.tedros.it.tools.module.evidence.model.CaptureEvidenceModel;
import org.tedros.it.tools.module.evidence.model.CreateJobEvidenceMV;
import org.tedros.it.tools.module.evidence.report.JobEvidenceReportMV;
import org.tedros.it.tools.module.useractivity.model.UserActivityMV;

@TView(items = {
		@TItem(title =  "User Activity Report", 
				//description = ItToolsKey.VIEW_CAPTURE_EVIDENCE_DESC, 
		modelView = UserActivityMV.class, model = ProductivityActivityDTO.class),
		
		@TItem(title =  ItToolsKey.VIEW_CAPTURE_EVIDENCE, description = ItToolsKey.VIEW_CAPTURE_EVIDENCE_DESC, 
			modelView = CaptureEvidenceMV.class, model = CaptureEvidenceModel.class),
		@TItem(title = ItToolsKey.VIEW_JOB_EVIDENCE, description = ItToolsKey.VIEW_JOB_EVIDENCE_DESC, 
			modelView = CreateJobEvidenceMV.class, model = JobEvidence.class),
		@TItem(title = ItToolsKey.VIEW_JOB_EVIDENCE_REPORT, description = ItToolsKey.VIEW_JOB_EVIDENCE_REPORT_DESC,
			modelView = JobEvidenceReportMV.class, model = JobEvidenceReportModel.class)})
@TSecurity(id = DomainApp.EVIDENCE_MANAGER_MODULE_ID, appName = ItToolsKey.APP_ITSUPPORT, 
moduleName = ItToolsKey.MODULE_ITSUPPORT_EVIDENCE, allowedAccesses = TAuthorizationType.MODULE_ACCESS)
public class JobEvidenceModule extends TModule {

}

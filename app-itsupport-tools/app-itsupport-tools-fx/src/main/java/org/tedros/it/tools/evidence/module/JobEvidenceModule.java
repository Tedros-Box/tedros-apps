package org.tedros.it.tools.evidence.module;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.it.tools.ItToolsKey;
import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.entity.JobEvidence;
import org.tedros.it.tools.evidence.model.CaptureEvidenceMV;
import org.tedros.it.tools.evidence.model.CaptureEvidenceModel;
import org.tedros.it.tools.evidence.model.JobEvidenceMV;

@TView(items = {
		@TItem(title = ItToolsKey.VIEW_ITSUPPORT_CAPTURE_EVIDENCE, 
				description = ItToolsKey.VIEW_ITSUPPORT_CAPTURE_EVIDENCE_DESC, 
				modelView = JobEvidenceMV.class, model = JobEvidence.class),
		@TItem(title = "Captura de evidencias", 
				modelView = CaptureEvidenceMV.class, model = CaptureEvidenceModel.class) })
@TSecurity(id = DomainApp.EVIDENCE_MANAGER_MODULE_ID, appName = ItToolsKey.APP_ITSUPPORT, 
moduleName = ItToolsKey.MODULE_ITSUPPORT_EVIDENCE, allowedAccesses = TAuthorizationType.MODULE_ACCESS)
public class JobEvidenceModule extends TModule {

}

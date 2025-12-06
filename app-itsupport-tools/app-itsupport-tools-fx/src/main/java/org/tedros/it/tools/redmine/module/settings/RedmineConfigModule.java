/**
 * 
 */
package org.tedros.it.tools.redmine.module.settings;

import org.tedros.api.presenter.view.ITView;
import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.it.tools.entity.JobEvidence;
import org.tedros.it.tools.evidence.model.EvidenceMonitorView;
import org.tedros.it.tools.evidence.model.JobEvidenceModelView;

import javafx.scene.Node;

/**
 * @author Davis Dun
 *
 */
@TView(items = {
		//@TItem(title = ItToolsKey.VIEW_ITSUPPORT_CAPTURE_EVIDENCE, description = ItToolsKey.VIEW_ITSUPPORT_CAPTURE_EVIDENCE_DESC, modelView = RedmineConfigMV.class, model = RedmineConfig.class),
		@TItem(title = "teste", modelView = JobEvidenceModelView.class, model = JobEvidence.class) })
//@TSecurity(id = DomainApp.EVIDENCE_MANAGER_MODULE_ID, appName = ItToolsKey.APP_ITSUPPORT, moduleName = ItToolsKey.MODULE_ITSUPPORT_EVIDENCE, allowedAccesses = TAuthorizationType.MODULE_ACCESS)
public class RedmineConfigModule extends TModule {
	
	public RedmineConfigModule() {
		super();
	}
	
	@Override
	public void tShowView(ITView view) {
		// TODO Auto-generated method stub
		getChildren().add(new EvidenceMonitorView());
	}

}

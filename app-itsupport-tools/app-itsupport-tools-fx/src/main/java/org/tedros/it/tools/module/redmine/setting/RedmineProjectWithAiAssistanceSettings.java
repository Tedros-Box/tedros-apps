package org.tedros.it.tools.module.redmine.setting;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.core.context.TedrosContext;
import org.tedros.fx.form.TSetting;
import org.tedros.it.tools.module.evidence.component.EvidenceMonitorView;
import org.tedros.it.tools.redmine.ai.function.RedmineApiPropertyUtil;
import org.tedros.it.tools.redmine.component.RedmineIssueSearchComponent;
import org.tedros.it.tools.redmine.gateway.RedmineApiGateway;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.scene.layout.VBox;

public class RedmineProjectWithAiAssistanceSettings extends TSetting {
	
	private ChangeListener<Boolean> exitListener;

    public RedmineProjectWithAiAssistanceSettings(ITComponentDescriptor descriptor) {
        super(descriptor);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void run() {
    	VBox node = super.getLayout("header");
    	
    	RedmineApiPropertyUtil propertyUtil = RedmineApiPropertyUtil.getInstance();
        RedmineApiGateway gateway = new RedmineApiGateway(propertyUtil.getRedmineUrl(), propertyUtil.getRedmineKey());
    	
    	RedmineIssueSearchComponent component = new RedmineIssueSearchComponent(gateway); 
    	node.getChildren().add(component);
		
		/*
		exitListener = (obs, oldVal, newVal) -> {
			if (newVal && evidenceMonitorView != null) {				
				evidenceMonitorView.shutdown();
				exitListener = null;
			}
		};
		
		TedrosContext.exitSystemProperty().addListener(new WeakChangeListener<>(exitListener));
		*/
    }

}

package org.tedros.it.tools.evidence.model;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.core.context.TedrosContext;
import org.tedros.fx.form.TSetting;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.scene.layout.VBox;

public class CaptureEvidenceSettings extends TSetting {
	
	private ChangeListener<Boolean> exitListener;
	private EvidenceMonitorView evidenceMonitorView;

    public CaptureEvidenceSettings(ITComponentDescriptor descriptor) {
        super(descriptor);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void run() {
    	VBox node = super.getLayout("header");
    	
    	evidenceMonitorView = new EvidenceMonitorView(); 
    	node.getChildren().add(evidenceMonitorView);
		
		exitListener = (obs, oldVal, newVal) -> {
			if (newVal && evidenceMonitorView != null) {				
				evidenceMonitorView.shutdown();
				exitListener = null;
			}
		};
		
		TedrosContext.exitSystemProperty().addListener(new WeakChangeListener<>(exitListener));
    }

}

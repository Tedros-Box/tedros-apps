package org.tedros.it.tools.evidence.model;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.fx.form.TSetting;

public class JobEvidenceSettings extends TSetting {

    public JobEvidenceSettings(ITComponentDescriptor descriptor) {
        super(descriptor);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void run() {

        super.getForm().gettPresenter().getView()
                .gettFormSpace().getChildren().add(new EvidenceMonitorView());

    }

}

package org.tedros.it.tools.module.evidence.component;

import java.io.File;

@FunctionalInterface
public interface EvidenceCaptureListener {
    void onCapture(String windowTitle, File evidenceFile);
}

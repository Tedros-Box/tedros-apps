package org.tedros.it.tools.evidence;

import java.io.File;

@FunctionalInterface
public interface EvidenceCaptureListener {
    void onCapture(String windowTitle, File evidenceFile);
}

package org.tedros.it.tools.evidence.model;

import java.io.File;
import java.util.Date;

public class EvidenceItem {
    private final String windowTitle;
    private final File file;
    private final Date timestamp;

    public EvidenceItem(String windowTitle, File file) {
        this.windowTitle = windowTitle;
        this.file = file;
        this.timestamp = new Date();
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    public File getFile() {
        return file;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}

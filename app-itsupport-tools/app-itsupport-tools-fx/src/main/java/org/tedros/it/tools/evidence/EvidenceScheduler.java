package org.tedros.it.tools.evidence;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;

public class EvidenceScheduler {

    private ScheduledExecutorService scheduler;
    // Define the interval in seconds between checks
    private int checkIntervalSeconds = 5;

    private String outputDir = System.getProperty("user.home") + File.separator + "TedrosEvidence";

    // Define the set of applications to monitor (e.g., for evidence)
    private final Set<String> targetApplications = ConcurrentHashMap.newKeySet();

    private final Set<EvidenceCaptureListener> listeners = ConcurrentHashMap.newKeySet();

    private boolean running = false;

    public EvidenceScheduler() {
        // Populate the set with partial or full window titles of the required
        // applications
        // Default values
        targetApplications.add("IntelliJ IDEA");
        targetApplications.add("Microsoft Teams");
        targetApplications.add("SAP GUI");
        targetApplications.add("Eclipse");
        targetApplications.add("Redmine");
        targetApplications.add("Excel");
    }

    public void addListener(EvidenceCaptureListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(EvidenceCaptureListener listener) {
        this.listeners.remove(listener);
    }

    public void addTargetApp(String appName) {
        if (appName != null && !appName.isBlank())
            targetApplications.add(appName);
    }

    public void removeTargetApp(String appName) {
        targetApplications.remove(appName);
    }

    public Set<String> getTargetApps() {
        return Collections.unmodifiableSet(targetApplications);
    }

    /**
     * Starts the periodic monitoring and screenshot capturing task.
     */
    public void startMonitoring() {
        if (running)
            return;

        scheduler = Executors.newSingleThreadScheduledExecutor();
        running = true;

        System.out.println("Monitoring started. Check interval: " + checkIntervalSeconds + " seconds.");

        scheduler.scheduleAtFixedRate(() -> {
            try {
                if (!running)
                    return;

                // 1. CHECK THE ACTIVE WINDOW TITLE
                String windowTitle = WindowMonitor.getForegroundWindowTitle();

                // 2. CHECK IF THE ACTIVE WINDOW IS A TARGET APPLICATION
                boolean isTargetApp = targetApplications.stream()
                        .anyMatch(app -> windowTitle.toLowerCase().contains(app.toLowerCase()));

                if (isTargetApp) {
                    // 3. CAPTURE THE SCREEN FOR EVIDENCE
                    java.awt.Rectangle windowBounds = WindowMonitor.getForegroundWindowBounds();

                    if (windowBounds != null) {
                        // 3. CAPTURE APENAS O MONITOR ATIVO
                        String timestamp = String.valueOf(System.currentTimeMillis());
                        String fileName = "evidence_" + timestamp + ".png";

                        File capturedFile = ScreenCaptureUtil.captureActiveMonitor(outputDir, fileName, windowBounds);

                        notifyListeners(windowTitle, capturedFile);

                        System.out.println("-> EVIDENCE CAPTURED (Target Monitor) for: " + windowTitle + " (File: "
                                + fileName + ")");
                    } else {
                        System.err.println("Could not get window bounds for " + windowTitle + ". Skipping capture.");
                    }
                }

            } catch (AWTException e) {
                System.err.println("ERROR during screen capture (AWT): " + e.getMessage());
            } catch (IOException e) {
                System.err.println("ERROR writing file: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Generic ERROR during monitoring: " + e.getMessage());
            }
        }, 0, checkIntervalSeconds, TimeUnit.SECONDS);
    }

    private void notifyListeners(String windowTitle, File capturedFile) {
        for (EvidenceCaptureListener l : listeners) {
            try {
                // Ensure UI updates happen on JavaFX Thread if needed,
                // but here we just dispatch. The listener implementation should handle
                // threading if it touches UI.
                // However, to be safe for JavaFX UI directly:
                Platform.runLater(() -> l.onCapture(windowTitle, capturedFile));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Stops the monitoring task and shuts down the scheduler.
     */
    public void stopMonitoring() {
        running = false;
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
            System.out.println("Monitoring stopped.");
        }
    }

    public int getCheckIntervalSeconds() {
        return checkIntervalSeconds;
    }

    public void setCheckIntervalSeconds(int checkIntervalSeconds) {
        this.checkIntervalSeconds = checkIntervalSeconds;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public boolean isRunning() {
        return running;
    }
}

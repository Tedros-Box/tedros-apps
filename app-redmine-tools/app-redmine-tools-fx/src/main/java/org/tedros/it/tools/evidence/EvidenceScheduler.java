package org.tedros.it.tools.evidence;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Set;
import java.util.HashSet;
import java.awt.AWTException;
import java.io.IOException;

public class EvidenceScheduler {

    private ScheduledExecutorService scheduler;
    // Define the interval in seconds between checks
    private final int CHECK_INTERVAL_SECONDS = 5; 
    
    // Define the set of applications to monitor (e.g., for evidence)
    private final Set<String> targetApplications = new HashSet<>();

    public EvidenceScheduler() {
        // Populate the set with partial or full window titles of the required applications
        targetApplications.add("IntelliJ IDEA");
        targetApplications.add("Microsoft Teams");
        targetApplications.add("SAP GUI"); 
        targetApplications.add("Eclipse");
        targetApplications.add("Redmine");
        targetApplications.add("Excel");
        // Add more apps as needed
    }

    /**
     * Starts the periodic monitoring and screenshot capturing task.
     */
    public void startMonitoring() {
        scheduler = Executors.newSingleThreadScheduledExecutor();

        System.out.println("Monitoring started. Check interval: " + CHECK_INTERVAL_SECONDS + " seconds.");

        scheduler.scheduleAtFixedRate(() -> {
            try {
                // 1. CHECK THE ACTIVE WINDOW TITLE
                String windowTitle = WindowMonitor.getForegroundWindowTitle();
                
                System.out.println("Active Window: " + windowTitle);

                // 2. CHECK IF THE ACTIVE WINDOW IS A TARGET APPLICATION
                boolean isTargetApp = targetApplications.stream()
                                                        .anyMatch(windowTitle::contains);

                if (isTargetApp) {
                    // 3. CAPTURE THE SCREEN FOR EVIDENCE
                	java.awt.Rectangle windowBounds = WindowMonitor.getForegroundWindowBounds();
                    
                    if (windowBounds != null) {
                        // 3. CAPTURE APENAS O MONITOR ATIVO
                        String timestamp = String.valueOf(System.currentTimeMillis());
                        String fileName = "evidence_" + timestamp + ".png";
                        
                        ScreenCaptureUtil.captureActiveMonitor(fileName, windowBounds);
                        
                        System.out.println("-> EVIDENCE CAPTURED (Target Monitor) for: " + windowTitle + " (File: " + fileName + ")");
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
        }, 0, CHECK_INTERVAL_SECONDS, TimeUnit.SECONDS);
    }

    /**
     * Stops the monitoring task and shuts down the scheduler.
     */
    public void stopMonitoring() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
            System.out.println("Monitoring stopped.");
        }
    }
}

package org.tedros.it.tools.module.evidence.component;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.tedros.util.TLoggerUtil;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javafx.application.Platform;

public class EvidenceScheduler implements NativeKeyListener {

	public static final String OUTPUT_DIR = System.getProperty("user.home") + File.separator + "TedrosEvidence";
	
    private ScheduledExecutorService scheduler;
    // Define the interval in seconds between checks
    private int checkIntervalSeconds = 3;
    private TimeUnit timeUnit = TimeUnit.MINUTES;

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
        targetApplications.add("Eclipse");
        targetApplications.add("Explorer");
        targetApplications.add("localhost");
        targetApplications.add("Redmine");
        targetApplications.add("Excel");
        targetApplications.add("Word");
        targetApplications.add("Adobe");
        targetApplications.add("postman");
        targetApplications.add("notepad");
        targetApplications.add("sna");
        
        try {
        	System.setProperty("jnativehook.lib.path", System.getProperty("java.io.tmpdir"));
        	if(!GlobalScreen.isNativeHookRegistered()) {
        		// Inicializar o gancho (Hook) nativo
                GlobalScreen.registerNativeHook();
        	}
        } catch (NativeHookException ex) {
            TLoggerUtil.error(EvidenceScheduler.class, "Error registering native hook", ex);
        }
                
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

        TLoggerUtil.info(EvidenceScheduler.class, "Monitoring started. Check interval: " + checkIntervalSeconds + " " + timeUnit.name().toLowerCase());
        
        scheduler.scheduleAtFixedRate(this::captureEvidence, 0, checkIntervalSeconds, timeUnit);
        
        // Adicionar este listener para ouvir as teclas
        GlobalScreen.addNativeKeyListener(this);
    }

	private void captureEvidence() {
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

		            File capturedFile = ScreenCaptureUtil.captureActiveMonitor(OUTPUT_DIR, fileName, windowBounds);

		           // Save Metadata (Sidecar file)
		            saveMetadata(windowTitle, timestamp, fileName, capturedFile);

		            notifyListeners(windowTitle, capturedFile);

		            TLoggerUtil.info(EvidenceScheduler.class, "-> EVIDENCE CAPTURED (Target Monitor) for: " + windowTitle + " (File: "
		                    + fileName + ")");
		        } else {
		        	TLoggerUtil.info(EvidenceScheduler.class, "Could not get window bounds for " + windowTitle + ". Skipping capture.");
		        }
		    }

		} catch (AWTException e) {
			TLoggerUtil.error(EvidenceScheduler.class, "Error during screen capture (AWT)", e);		    
		} catch (IOException e) {
		    TLoggerUtil.error(EvidenceScheduler.class, "ERROR writing file", e);
		} catch (Exception e) {
			TLoggerUtil.error(EvidenceScheduler.class, "Generic ERROR during monitoring", e);
		}
	}

	private void saveMetadata(String windowTitle, String timestamp, String fileName, File capturedFile) {
		try {
			java.util.Properties props = new java.util.Properties();
			props.setProperty("windowTitle", windowTitle);
			props.setProperty("timestamp", timestamp);
			
			String metaFileName = fileName.replace(".png", ".properties");
			// Ensure we save in the same directory as the image
			File metaFile = new File(capturedFile.getParentFile(), metaFileName);
			
			try(java.io.FileOutputStream fos = new java.io.FileOutputStream(metaFile)){
				props.store(fos, "Evidence Metadata");
			}
		} catch(Exception ex) {
			TLoggerUtil.error(EvidenceScheduler.class, "Error saving metadata", ex);
		}
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
            TLoggerUtil.info(EvidenceScheduler.class, "Monitoring stopped.");
        }
        
        // Remove este listener para ouvir as teclas
        GlobalScreen.removeNativeKeyListener(this);
    }
    
    public void destroy() {
		stopMonitoring();
		listeners.clear();
		targetApplications.clear();
		scheduler = null;
		try {
			GlobalScreen.unregisterNativeHook();
		} catch (NativeHookException e) {
			TLoggerUtil.error(EvidenceScheduler.class, "Error unregistering native hook", e);
		}
	}

    public int getCheckIntervalSeconds() {
        return checkIntervalSeconds;
    }

    public void setCheckIntervalSeconds(int checkIntervalSeconds) {
        this.checkIntervalSeconds = checkIntervalSeconds;
    }

    public boolean isRunning() {
        return running;
        
    }
    
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        int modifiers = e.getModifiers();
        
        // Verifica se AMBAS as teclas (Ctrl E Alt) estão pressionadas
        boolean isCtrlPressed = (modifiers & NativeKeyEvent.CTRL_MASK) != 0;
        boolean isAltPressed = (modifiers & NativeKeyEvent.ALT_MASK) != 0;

        // VC_PRINTSCREEN é a constante para a tecla Print Screen
        if (e.getKeyCode() == NativeKeyEvent.VC_PRINTSCREEN && isCtrlPressed && isAltPressed) {
            
            Platform.runLater(() -> {
                TLoggerUtil.info(EvidenceScheduler.class, "Print Screen Captured via Global Hook (Ctrl + Alt + PrtSc).");
                captureEvidence();
            });
        }
    }
    
    // Métodos obrigatórios da interface que não precisamos usar agora
    @Override
    public void nativeKeyReleased(NativeKeyEvent e) { }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) { }
}

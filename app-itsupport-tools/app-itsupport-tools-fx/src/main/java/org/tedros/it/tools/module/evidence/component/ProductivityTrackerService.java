package org.tedros.it.tools.module.evidence.component;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseMotionListener;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import org.tedros.core.context.TedrosContext;
import org.tedros.core.security.model.TUser;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.it.tools.ejb.controller.IProductivityActivityController;
import org.tedros.it.tools.model.ProductivityActivityDTO;
import org.tedros.util.TLoggerUtil;

/**
 * Background service that monitors productivity activity (window title,
 * mouse interactions, keyboard events) without recording keystrokes.
 */
public class ProductivityTrackerService implements NativeKeyListener, NativeMouseListener, NativeMouseMotionListener {

    private final ScheduledExecutorService scheduler;
    private final ConcurrentLinkedQueue<ProductivityActivityDTO> activityQueue;

    private final AtomicLong mouseEventCount;
    private final AtomicLong keyboardEventCount;
    private final TUser loggedUser;

    public ProductivityTrackerService() {
        // Use a dedicated single-thread executor to completely isolate this tracking
        // from any other application tasks (like the 3-minute screenshot task).
        this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "ProductivityTracker-Thread");
            t.setDaemon(true); // Don't block application shutdown
            return t;
        });
        this.loggedUser = TedrosContext.getLoggedUser();
        this.activityQueue = new ConcurrentLinkedQueue<>();
        this.mouseEventCount = new AtomicLong(0);
        this.keyboardEventCount = new AtomicLong(0);
    }

    public void startTracking(long period, TimeUnit unit) {
        // Disable JNativeHook's verbose logging to prevent log flooding
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);

        try {
            if (!GlobalScreen.isNativeHookRegistered()) {
                GlobalScreen.registerNativeHook();
            }
            GlobalScreen.addNativeKeyListener(this);
            GlobalScreen.addNativeMouseListener(this);
            GlobalScreen.addNativeMouseMotionListener(this);
        } catch (Exception e) {
            System.err.println("Error registering global hook for productivity tracking: " + e.getMessage());
        }

        // Schedule periodic snapshotting of activity and window title
        scheduler.scheduleAtFixedRate(this::snapshotActivity, 0, period, unit);
    }

    public void stopTracking() {
        scheduler.shutdown();
        GlobalScreen.removeNativeKeyListener(this);
        GlobalScreen.removeNativeMouseListener(this);
        GlobalScreen.removeNativeMouseMotionListener(this);
        try {
            if (GlobalScreen.isNativeHookRegistered()) {
                GlobalScreen.unregisterNativeHook();
            }
        } catch (Exception e) {
        	TLoggerUtil.error(e.getMessage(), e);
        }
    }
    
    private void saveActivity() {
		
    	try(TEjbServiceLocator locator = TEjbServiceLocator.getInstance()){
    		IProductivityActivityController controller = locator.lookup(IProductivityActivityController.JNDI_NAME);
    		List<ProductivityActivityDTO> activitiesToSave = new ArrayList<>();
    		while (!activityQueue.isEmpty()) {
				activitiesToSave.add(activityQueue.poll());
			}
    		
    		if(!activitiesToSave.isEmpty()) {
				controller.saveActivity(loggedUser.getAccessToken(), activitiesToSave);
			}
    		
    	} catch (NamingException e) {
			TLoggerUtil.error(e.getMessage(), e);
		}
    	
	}

    private void snapshotActivity() {
        try {
            // 1. Capture OS User
            String userName = loggedUser.getName();
            Long userId = loggedUser.getId();

            // 2. Capture Window Title (Windows specific via JNA)
            String activeWindowTitle = getActiveWindowTitle();

            // 3. Capture and reset interaction counts atomically
            long mCount = mouseEventCount.getAndSet(0);
            long kCount = keyboardEventCount.getAndSet(0);

            // 4. Create record
            ProductivityActivityDTO dto = new ProductivityActivityDTO(
                    LocalDateTime.now(),
                    userName,
                    userId,
                    activeWindowTitle,
                    mCount,
                    kCount);

            // 5. Enqueue for later processing
            activityQueue.offer(dto);

            if(activityQueue.size() >= 10) { // Arbitrary batch size for saving
				saveActivity();
			}

        } catch (Exception e) {
            System.err.println("Error processing activity snapshot: " + e.getMessage());
        }
    }

    private String getActiveWindowTitle() {
        String windowTitle = "Unknown Window/OS";
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("win")) {
            try {
                HWND hwnd = User32.INSTANCE.GetForegroundWindow();
                if (hwnd != null) {
                    char[] windowText = new char[512];
                    User32.INSTANCE.GetWindowText(hwnd, windowText, 512);
                    windowTitle = Native.toString(windowText).trim();
                }
            } catch (Exception e) {
                windowTitle = "Error fetching title";
            }
        } else {
            windowTitle = "OS not supported for window title capture";
        }

        return windowTitle;
    }

    public ConcurrentLinkedQueue<ProductivityActivityDTO> getActivityQueue() {
        return activityQueue;
    }

    // --- Interaction Listeners (Volume Tracking Only) ---

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        keyboardEventCount.incrementAndGet();
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        // Ignored to avoid double counting
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        // Ignored, privacy enforcement: we do not track typed characters
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent e) {
        mouseEventCount.incrementAndGet();
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
        // Ignored to avoid double counting
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {
        // Ignored to avoid double counting
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {
        mouseEventCount.incrementAndGet();
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e) {
        mouseEventCount.incrementAndGet();
    }
}

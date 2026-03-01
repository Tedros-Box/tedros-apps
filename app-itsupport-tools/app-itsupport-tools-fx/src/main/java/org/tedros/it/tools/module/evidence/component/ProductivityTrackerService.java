package org.tedros.it.tools.module.evidence.component;

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

import org.tedros.core.context.TedrosContext;
import org.tedros.core.security.model.TUser;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.it.tools.ejb.controller.IProductivityActivityController;
import org.tedros.it.tools.model.ProductivityActivityDTO;
import org.tedros.util.TLoggerUtil;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseMotionListener;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

/**
 * Background service that monitors productivity activity (window title,
 * mouse interactions, keyboard events) without recording keystrokes.
 */
public class ProductivityTrackerService implements NativeKeyListener, NativeMouseListener, NativeMouseMotionListener {

    private final ScheduledExecutorService scheduler;
    private final ConcurrentLinkedQueue<ProductivityActivityDTO> activityQueue;
    private final AtomicLong mouseEventCount;
    private final AtomicLong keyboardEventCount;
    private final AtomicLong lastMouseEventTime; // para throttling
    private final TUser loggedUser;

    private volatile boolean running = false;

    public ProductivityTrackerService() {
        this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "ProductivityTracker-Thread");
            t.setDaemon(true);
            return t;
        });

        this.loggedUser = TedrosContext.getLoggedUser();
        this.activityQueue = new ConcurrentLinkedQueue<>();
        this.mouseEventCount = new AtomicLong(0);
        this.keyboardEventCount = new AtomicLong(0);
        this.lastMouseEventTime = new AtomicLong(0);
    }

    public void startTracking(long period, TimeUnit unit) {
        if (running) {
            TLoggerUtil.warn(getClass(), "ProductivityTrackerService já está em execução. Ignorando chamada duplicada.");
            return;
        }

        running = true;

        // Desabilita log verboso do JNativeHook
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
            TLoggerUtil.error("Erro ao registrar global hook para rastreamento de produtividade", e);
        }

        scheduler.scheduleAtFixedRate(this::snapshotActivity, 0, period, unit);
    }

    public void stopTracking() {
        if (!running) return;

        running = false;
        scheduler.shutdown();

        try {
            // Último snapshot + envio antes de parar
            snapshotActivity();
            if (!activityQueue.isEmpty()) {
                saveActivity();
            }

            if (!scheduler.awaitTermination(8, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            TLoggerUtil.error("Interrupted ao parar ProductivityTrackerService", e);
        } finally {
            GlobalScreen.removeNativeKeyListener(this);
            GlobalScreen.removeNativeMouseListener(this);
            GlobalScreen.removeNativeMouseMotionListener(this);

            try {
                if (GlobalScreen.isNativeHookRegistered()) {
                    GlobalScreen.unregisterNativeHook();
                }
            } catch (Exception e) {
                TLoggerUtil.error("Erro ao remover native hook", e);
            }
        }
    }

    private void saveActivity() {
        List<ProductivityActivityDTO> batch = new ArrayList<>();
        ProductivityActivityDTO dto;
        while ((dto = activityQueue.poll()) != null) {
            batch.add(dto);
        }

        if (batch.isEmpty()) return;

        try (TEjbServiceLocator locator = TEjbServiceLocator.getInstance()) {
            IProductivityActivityController controller = locator.lookup(IProductivityActivityController.JNDI_NAME);
            controller.saveActivity(loggedUser.getAccessToken(), batch);
        } catch (Exception e) { 
            TLoggerUtil.error("Falha ao salvar batch de atividades. Reenfileirando para próxima tentativa...", e);
            activityQueue.addAll(batch); // protege contra perda de dados
        }
    }

    private void snapshotActivity() {
        if (!running) return;

        try {
            String userName = loggedUser.getName();
            Long userId = loggedUser.getId();
            String activeWindowTitle = getActiveWindowTitle();

            long mCount = mouseEventCount.getAndSet(0);
            long kCount = keyboardEventCount.getAndSet(0);

            ProductivityActivityDTO dto = new ProductivityActivityDTO(
                    LocalDateTime.now(),
                    userName,
                    userId,
                    activeWindowTitle,
                    mCount,
                    kCount);

            activityQueue.offer(dto);

            if (activityQueue.size() >= 3) {
                saveActivity();
            }
        } catch (Exception e) {
            TLoggerUtil.error("Erro ao processar snapshot de atividade", e);
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
                    if (windowTitle.isEmpty()) {
                        windowTitle = "Unnamed Window";
                    }
                }
            } catch (Exception e) {
                TLoggerUtil.error("Erro ao obter título da janela ativa", e);
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

    // ==================== LISTENERS ====================

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        keyboardEventCount.incrementAndGet();
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) { /* ignorado */ }
    @Override
    public void nativeKeyTyped(NativeKeyEvent e) { /* ignorado - privacidade */ }

    @Override
    public void nativeMouseClicked(NativeMouseEvent e) {
        mouseEventCount.incrementAndGet(); // cliques sempre contam
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent e) { /* ignorado */ }
    @Override
    public void nativeMouseReleased(NativeMouseEvent e) { /* ignorado */ }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {
        throttleMouseEvent();
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e) {
        throttleMouseEvent();
    }

    private void throttleMouseEvent() {
        long now = System.currentTimeMillis();
        long last = lastMouseEventTime.get();
        if (now - last > 150) { // máximo ~6-7 eventos de movimento por segundo
            lastMouseEventTime.set(now);
            mouseEventCount.incrementAndGet();
        }
    }
}
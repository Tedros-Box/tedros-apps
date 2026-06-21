package org.tedros.it.tools.module.employeeactivity.component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
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
 * Otimizado para alta concorrência e baixo consumo de rede.
 */
public class EmployeeActivityTrackerService implements NativeKeyListener, NativeMouseListener, NativeMouseMotionListener {

    private final ScheduledExecutorService scheduler;
    private final ExecutorService networkExecutor; // Pool dedicado para chamadas de rede (EJB)
    private final LinkedBlockingQueue<ProductivityActivityDTO> activityQueue; // Fila com limite
    
    private final AtomicLong mouseEventCount;
    private final AtomicLong keyboardEventCount;
    private final AtomicLong lastMouseEventTime; // para throttling
    private final TUser loggedUser;

    private volatile boolean running = false;
    
    // Configurações de Batch e Jitter (Aleatoriedade)
    private int currentBatchTarget;
    private final Random random = new Random();
    private static final int MIN_BATCH_SIZE = 15; 
    private static final int MAX_BATCH_SIZE = 30;
    private static final int MAX_QUEUE_CAPACITY = 2000; // ~33 horas de retenção offline (proteção contra OOM)

    public EmployeeActivityTrackerService() {
        this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "ProductivityTracker-Thread");
            t.setDaemon(true);
            return t;
        });

        this.networkExecutor = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, "ProductivityNetwork-Thread");
            t.setDaemon(true);
            return t;
        });

        this.loggedUser = TedrosContext.getLoggedUser();
        this.activityQueue = new LinkedBlockingQueue<>(MAX_QUEUE_CAPACITY);
        this.mouseEventCount = new AtomicLong(0);
        this.keyboardEventCount = new AtomicLong(0);
        this.lastMouseEventTime = new AtomicLong(0);
        
        updateBatchTarget(); // Define o primeiro alvo de envio aleatório
    }

    public void startTracking(long period, TimeUnit unit) {
        if (running) {
            TLoggerUtil.warn(getClass(), "EmployeeActivityTrackerService já está em execução. Ignorando chamada duplicada.");
            return;
        }

        running = true;

        // Desabilita log verboso do JNativeHook
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);

        try {
        	System.setProperty("jnativehook.lib.path", System.getProperty("java.io.tmpdir"));
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
                triggerAsyncSave(); // Manda o restante para o backend
            }

            if (!scheduler.awaitTermination(8, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            TLoggerUtil.error("Interrupted ao parar EmployeeActivityTrackerService", e);
        } finally {
            networkExecutor.shutdown(); // Finaliza a thread de rede
            
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

    private void updateBatchTarget() {
        // Define o gatilho de envio para um número aleatório entre MIN e MAX
        this.currentBatchTarget = MIN_BATCH_SIZE + random.nextInt((MAX_BATCH_SIZE - MIN_BATCH_SIZE) + 1);
    }

    private void snapshotActivity() {
        if (!running) return;

        try {
            long mCount = mouseEventCount.getAndSet(0);
            long kCount = keyboardEventCount.getAndSet(0);
            String currentWindowTitle = getActiveWindowTitle();
            
            ProductivityActivityDTO dto = new ProductivityActivityDTO(
                    LocalDateTime.now(),
                    loggedUser.getName(),
                    loggedUser.getId(),
                    currentWindowTitle,
                    mCount,
                    kCount);

            // Tenta adicionar à fila. Se estiver cheia (servidor offline há muito tempo), remove o mais antigo.
            if (!activityQueue.offer(dto)) {
                activityQueue.poll(); 
                activityQueue.offer(dto);
            }

            // Verifica se atingiu o alvo do batch com Jitter
            if (activityQueue.size() >= currentBatchTarget) {
                updateBatchTarget(); // Sorteia o próximo alvo
                triggerAsyncSave();  // Envia sem bloquear a thread do snapshot
            }
        } catch (Exception e) {
            TLoggerUtil.error("Erro ao processar snapshot de atividade", e);
        }
    }

    private void triggerAsyncSave() {
        // Submit para a thread separada de rede
        networkExecutor.submit(() -> {
            List<ProductivityActivityDTO> batch = new ArrayList<>();
            // Move todos os itens atuais da fila para a lista batch de forma thread-safe
            activityQueue.drainTo(batch);

            if (batch.isEmpty()) return;

            try (TEjbServiceLocator locator = TEjbServiceLocator.getInstance()) {
                IProductivityActivityController controller = locator.lookup(IProductivityActivityController.JNDI_NAME);
                controller.saveActivity(loggedUser.getAccessToken(), batch);
            } catch (Exception e) { 
                TLoggerUtil.error("Falha ao salvar batch de atividades. Reenfileirando para próxima tentativa...", e);
                // Se falhar, devolve para a fila (protegendo contra estouro da capacidade)
                for (ProductivityActivityDTO dto : batch) {
                    if (!activityQueue.offer(dto)) {
                        break; // Se encheu a fila novamente, descarta o resto do batch antigo
                    }
                }
            }
        });
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

    public LinkedBlockingQueue<ProductivityActivityDTO> getActivityQueue() {
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
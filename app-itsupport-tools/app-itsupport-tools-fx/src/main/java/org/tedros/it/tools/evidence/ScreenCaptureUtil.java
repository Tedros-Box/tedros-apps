package org.tedros.it.tools.evidence;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import org.tedros.util.TDateUtil;

public class ScreenCaptureUtil {

    /**
     * Captures only the monitor screen where the active window is primarily
     * located.
     * 
     * @param outputDir    The directory to save the file.
     * @param fileName     The name of the file to save.
     * @param windowBounds The coordinates and size of the active window.
     * @return The File object of the saved image.
     * @throws AWTException If low-level input control is not allowed.
     * @throws IOException  If file writing fails.
     */
    public static File captureActiveMonitor(String outputDir, String fileName, Rectangle windowBounds)
            throws AWTException, IOException {

        if (windowBounds == null) {
            throw new IllegalArgumentException("Window bounds cannot be null.");
        }

        // 1. Encontra o monitor que contém a maior parte da janela ativa
        GraphicsDevice targetDevice = getDeviceFromBounds(windowBounds);

        if (targetDevice == null) {
            System.err.println("Could not find the monitor containing the window. Capturing the default screen.");
            // Fallback: Captura o monitor principal se o alvo não for encontrado
            targetDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        }

        // 2. Obtém os limites (bounds) exatos do monitor alvo
        GraphicsConfiguration gc = targetDevice.getDefaultConfiguration();
        Rectangle monitorBounds = gc.getBounds();

        // 3. Cria o objeto Robot e captura a área do monitor alvo
        Robot robot = new Robot(targetDevice); // É mais seguro criar o Robot com o dispositivo alvo

        // Captura o retângulo completo do monitor (Bounds)
        BufferedImage image = robot.createScreenCapture(monitorBounds);
        String workingDayFolder = TDateUtil.format(new Date(), "yyyy-MM-dd");
        // 4. Salva a imagem
        File outputFile = new File(outputDir+File.separator+workingDayFolder, fileName);
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }
        ImageIO.write(image, "png", outputFile);

        return outputFile;
    }

    /**
     * Helper method to find the GraphicsDevice (monitor) where the window is
     * located.
     * It checks which monitor contains the center point of the window's bounds.
     */
    private static GraphicsDevice getDeviceFromBounds(Rectangle bounds) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gds = ge.getScreenDevices();

        // Use o ponto central da janela como referência
        int centerX = bounds.x + bounds.width / 2;
        int centerY = bounds.y + bounds.height / 2;

        for (GraphicsDevice gd : gds) {
            Rectangle monitorBounds = gd.getDefaultConfiguration().getBounds();
            // Verifica se o ponto central da janela está dentro deste monitor
            if (monitorBounds.contains(centerX, centerY)) {
                return gd;
            }
        }
        return null; // Monitor não encontrado
    }
}

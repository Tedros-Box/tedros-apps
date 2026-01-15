package org.tedros.it.tools.module.evidence.component;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;

public class WindowMonitor {

    /**
     * Retrieves the title of the currently focused (foreground) window.
     * * NOTE: This example uses JNA (Java Native Access) for Windows (User32.dll). 
     * You MUST include JNA dependencies for this to work on Windows.
     * For other OSes, the implementation must be adapted (e.g., X11 commands for Linux).
     * * @return The title string of the foreground window, or a failure message.
     */
    public static String getForegroundWindowTitle() {
        try {
            // Get the handle of the active window
            HWND foregroundWindow = User32.INSTANCE.GetForegroundWindow();
            
            if (foregroundWindow == null) {
                return "No Active Window";
            }

            // Retrieve the window title text
            char[] windowText = new char[512];
            User32.INSTANCE.GetWindowText(foregroundWindow, windowText, 512);
            
            return new String(windowText).trim();
            
        } catch (Exception e) {
            // Catch potential JNA errors if dependencies are missing or OS is not Windows
            System.err.println("Error accessing native OS API (JNA): " + e.getMessage());
            return "ERROR: JNA/OS Access Failed";
        }
    }
    
    /**
     * Retrieves the bounding rectangle (coordinates) of the currently focused window.
     * @return A Rectangle object representing the window's position and size, or null if retrieval fails.
     */
    public static java.awt.Rectangle getForegroundWindowBounds() {
        try {
            HWND foregroundWindow = User32.INSTANCE.GetForegroundWindow();
            if (foregroundWindow == null) {
                return null;
            }
            
            RECT rect = new RECT();
            // GetWindowRect retrieves the dimensions of the bounding rectangle of the specified window
            User32.INSTANCE.GetWindowRect(foregroundWindow, rect);

            return new java.awt.Rectangle(
                rect.left, 
                rect.top, 
                rect.right - rect.left, 
                rect.bottom - rect.top
            );
            
        } catch (Exception e) {
            System.err.println("Error accessing native OS API (JNA) for bounds: " + e.getMessage());
            return null;
        }
    }
}

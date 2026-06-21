package org.tedros.it.tools.employeeactivity.ai.function;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class AIProductivityLogDTO {
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime timestamp;
    private String activeWindowTitle;
    private long mouseEventCount;
    private long keyboardEventCount;
    
    public AIProductivityLogDTO() {
		
	}
    
    public AIProductivityLogDTO(LocalDateTime timestamp, String activeWindowTitle, long mouseEventCount,
			long keyboardEventCount) {
		this.timestamp = timestamp;
		this.activeWindowTitle = activeWindowTitle;
		this.mouseEventCount = mouseEventCount;
		this.keyboardEventCount = keyboardEventCount;
	}
    
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public String getActiveWindowTitle() {
		return activeWindowTitle;
	}
	public void setActiveWindowTitle(String activeWindowTitle) {
		this.activeWindowTitle = activeWindowTitle;
	}
	public long getMouseEventCount() {
		return mouseEventCount;
	}
	public void setMouseEventCount(long mouseEventCount) {
		this.mouseEventCount = mouseEventCount;
	}
	public long getKeyboardEventCount() {
		return keyboardEventCount;
	}
	public void setKeyboardEventCount(long keyboardEventCount) {
		this.keyboardEventCount = keyboardEventCount;
	}
    
    

    
}
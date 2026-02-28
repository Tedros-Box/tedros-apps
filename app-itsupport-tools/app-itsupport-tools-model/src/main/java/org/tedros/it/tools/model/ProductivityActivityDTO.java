package org.tedros.it.tools.model;

import java.time.LocalDateTime;

import org.tedros.server.entity.TEntity;

/**
 * Data Transfer Object holding the captured productivity activity
 * data for a given timestamp.
 */
public class ProductivityActivityDTO extends TEntity {
	
	private static final long serialVersionUID = -2453352031949022182L;
	
	private LocalDateTime timestamp;
	private String username;
	private Long userId;
	private String activeWindowTitle;
	private long mouseEventCount;
	private long keyboardEventCount;
	
	public ProductivityActivityDTO(LocalDateTime timestamp, String username, Long userId, String activeWindowTitle,
			long mouseEventCount, long keyboardEventCount) {
		this.timestamp = timestamp;
		this.username = username;
		this.userId = userId;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

/**
 * 
 */
package org.tedros.samples.module.control.model;

import org.tedros.server.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public class WebModel implements ITModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String content;

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
}

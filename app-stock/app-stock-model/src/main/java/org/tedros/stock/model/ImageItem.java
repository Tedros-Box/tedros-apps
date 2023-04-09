/**
 * 
 */
package org.tedros.stock.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.tedros.server.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public class ImageItem implements ITModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6705260757523611315L;

	private byte[] bytes;
	private InputStream inputStream;
	/**
	 * 
	 */
	public ImageItem(byte[] bytes) {
		this.bytes = bytes;
	}
	public void initInputStream() {
		if(bytes!=null && bytes.length>0) {
			inputStream = new ByteArrayInputStream(bytes);
		}
	}
	
	public void closeInputStream() throws IOException {
		if(this.inputStream!=null) {
			this.inputStream.close();
		}
	}
	/**
	 * @return the bytes
	 */
	public byte[] getBytes() {
		return bytes;
	}
	/**
	 * @param bytes the bytes to set
	 */
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}
	/**
	 * @param inputStream the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}

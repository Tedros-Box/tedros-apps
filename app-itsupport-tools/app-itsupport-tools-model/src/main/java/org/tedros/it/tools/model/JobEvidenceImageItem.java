/**
 * 
 */
package org.tedros.it.tools.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import org.tedros.it.tools.entity.JobEvidenceItem;
import org.tedros.server.model.ITModel;

/**
 * @author Davis Gordon
 *
 */
public class JobEvidenceImageItem implements ITModel {

	private static final long serialVersionUID = 1547275045900552846L;
	
	private static final String DDMMYYYY_HHMM = "dd/MM/yyyy HH:mm";
	
	private String evidenceDateTime;
	private String description;	
	private byte[] bytes;
	private InputStream inputStream;
	
	/**
	 * 
	 */
	public JobEvidenceImageItem(JobEvidenceItem item) {
		this.evidenceDateTime = new SimpleDateFormat(DDMMYYYY_HHMM).format(item.getEvidenceDate());
		this.description =  item.getDescription();		
		this.bytes = item.getFile().getByteEntity().getBytes();
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getEvidenceDateTime() {
		return evidenceDateTime;
	}

	public void setEvidenceDateTime(String evidenceDateTime) {
		this.evidenceDateTime = evidenceDateTime;
	}

}

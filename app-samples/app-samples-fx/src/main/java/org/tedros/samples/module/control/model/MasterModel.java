package org.tedros.samples.module.control.model;

import java.util.Date;

import org.tedros.server.model.ITModel;

public class MasterModel implements ITModel {

	private static final long serialVersionUID = 1L;

	private String string0 = "Master string field";
	
	private Integer integer0 = 2306;
	
	private Long long0 = 2306L;
	
	private Double double0 = 2306.10;
	
	private Date date0 = new Date();
	
	private DetailModel detail0 = new DetailModel();

	/**
	 * @return the string0
	 */
	public String getString0() {
		return string0;
	}

	/**
	 * @param string0 the string0 to set
	 */
	public void setString0(String string0) {
		this.string0 = string0;
	}

	/**
	 * @return the integer0
	 */
	public Integer getInteger0() {
		return integer0;
	}

	/**
	 * @param integer0 the integer0 to set
	 */
	public void setInteger0(Integer integer0) {
		this.integer0 = integer0;
	}

	/**
	 * @return the long0
	 */
	public Long getLong0() {
		return long0;
	}

	/**
	 * @param long0 the long0 to set
	 */
	public void setLong0(Long long0) {
		this.long0 = long0;
	}

	/**
	 * @return the double0
	 */
	public Double getDouble0() {
		return double0;
	}

	/**
	 * @param double0 the double0 to set
	 */
	public void setDouble0(Double double0) {
		this.double0 = double0;
	}

	/**
	 * @return the date0
	 */
	public Date getDate0() {
		return date0;
	}

	/**
	 * @param date0 the date0 to set
	 */
	public void setDate0(Date date0) {
		this.date0 = date0;
	}

	/**
	 * @return the detail0
	 */
	public DetailModel getDetail0() {
		return detail0;
	}

	/**
	 * @param detail0 the detail0 to set
	 */
	public void setDetail0(DetailModel detail0) {
		this.detail0 = detail0;
	}
	
}

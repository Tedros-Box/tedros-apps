/**
 * 
 */
package org.tedros.stock.ai.function;

import java.util.Date;

import org.tedros.stock.entity.Product;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * 
 */
@JsonClassDescription("The product attributes")
public class ProductParam {
	
	private String code;
	
	private String name;
	
	private String description;
	
	private String trademark;
	
	private String unitMeasure;
		
	private Double measure;
	
	private String size;
	
	private Double weight;
	
	@JsonPropertyDescription("the creation date, never fill in this field; instead, use beginDate and/or endDate")
	private Date insertDate;
	
	@JsonPropertyDescription("Used to find products created on "
			+ "or after this date with the endDate field. "
			+ "[Acceptable timestamp: ISO 8601 Date string, ex: yyyy-MM-dd'T'00:00:00]")
	private Date beginDate;
	
	@JsonPropertyDescription("use to find products created up to this date. "
			+ "[Acceptable timestamp: ISO 8601 Date string, ex: yyyy-MM-dd'T'23:59:59]")
	private Date endDate;
	
	public ProductParam() {
		
	}

	public ProductParam(Product p) {
		this.code = p.getCode();
		this.name = p.getName();
		this.description = p.getDescription();
		this.trademark = p.getTrademark();
		this.unitMeasure = p.getUnitMeasure();
		this.measure = p.getMeasure();
		this.size = p.getSize();
		this.weight = p.getWeight();
		this.insertDate = p.getInsertDate();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTrademark() {
		return trademark;
	}

	public void setTrademark(String trademark) {
		this.trademark = trademark;
	}

	public String getUnitMeasure() {
		return unitMeasure;
	}

	public void setUnitMeasure(String unitMeasure) {
		this.unitMeasure = unitMeasure;
	}

	public Double getMeasure() {
		return measure;
	}

	public void setMeasure(Double measure) {
		this.measure = measure;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	
	

}

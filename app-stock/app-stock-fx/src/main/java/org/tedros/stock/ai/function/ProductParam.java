/**
 * 
 */
package org.tedros.stock.ai.function;

import java.util.Date;

import org.tedros.stock.entity.Product;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 */
@Getter @Setter @ToString
@JsonClassDescription("Search criteria for products. Fill fields to narrow down the results.")
public class ProductParam {
	
	private String code;
	
	private String name;
	
	private String description;
	
	private String trademark;
	
	private String unitMeasure;
		
	private Double measure;
	
	private String size;
	
	private Double weight;
	
	@JsonPropertyDescription("Internal creation timestamp. Do NOT fill this field directly; use 'beginDate' and 'endDate' for range filtering.")
	private Date insertDate;
	
	@JsonPropertyDescription("Start date for the creation range filter (Inclusive). "
			+ "Format: ISO 8601 (yyyy-MM-dd'T'HH:mm:ss). "
			+ "(e.g., '2026-01-02'T'00:00:00')")
	private Date beginDate;
	
	@JsonPropertyDescription("End date for the creation range filter (Exclusive/Inclusive based on logic). "
			+ "Format: ISO 8601 (yyyy-MM-dd'T'HH:mm:ss). "
			+ "(e.g., '2026-01-03'T'23:59:59'")
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

}

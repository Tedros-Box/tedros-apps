/**
 * 
 */
package com.tedros.location.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.tedros.common.model.TFileEntity;
import com.tedros.ejb.base.annotation.TCaseSensitive;
import com.tedros.ejb.base.annotation.TEntityImportRule;
import com.tedros.ejb.base.annotation.TFieldImportRule;
import com.tedros.ejb.base.annotation.TFileType;
import com.tedros.ejb.base.entity.TEntity;
import com.tedros.location.domain.DomainSchema;
import com.tedros.location.domain.DomainTables;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.country, schema = DomainSchema.schema, 
uniqueConstraints= {@UniqueConstraint(name="countryCodeUnIdx", columnNames = { "iso2Code" })} )
@TEntityImportRule(description = "#{country.import.rule.desc}", 
fileType = { TFileType.CSV, TFileType.XLS })
public class Country extends TEntity {

	private static final long serialVersionUID = 1L;

	@Column(length=2, nullable=false)
	@TFieldImportRule(required = true, 
	description = "#{label.country.code} (ISO2)", column = "iso2", 
	example="BR", caseSensitive=TCaseSensitive.UPPER, maxLength=2)
	private String iso2Code;
	
	@Column(length=60, nullable=false)
	@TFieldImportRule(required = true, 
	description = "#{label.name}", column = "name", 
	example="Brazil", maxLength=60)
	private String name;
	
	@Column(length=3, nullable=false)
	@TFieldImportRule(required = true, 
	description = "#{label.country.code} (ISO3)", column = "iso3", 
	example="BRA", caseSensitive=TCaseSensitive.UPPER, maxLength=3)
	private String iso3Code;
	
	@Column
	@TFieldImportRule(required = false, 
	description = "#{label.numeric.code}", column = "numeric_code", 
	example="76", numberType=Long.class)
	private Long numericCode;
	
	@Column(length=120, nullable=false)
	@TFieldImportRule(required = true, 
	description = "#{label.capital}", column = "capital", 
	example="Brasilia", maxLength=120)
	private String capital;
	
	@Column(length=60)
	@TFieldImportRule(required = false, 
	description = "#{label.population.name}", column = "population_name", 
	example="Brazilians", maxLength=60)
	private String populationName;
	
	@Column
	@TFieldImportRule(required = false, 
	description = "#{label.total.area}", column = "total_area", 
	example="8514877", numberType=Long.class)
	private Long totalArea;
	
	@Column
	@TFieldImportRule(required = false, 
	description = "#{label.population}", column = "population", 
	example="212559417", numberType=Long.class)
	private Long population;
	
	@Column
	@TFieldImportRule(required = false, 
	description = "#{label.idd.code}", column = "idd_code", 
	example="55", numberType=Integer.class)
	private Integer iddCode;
	
	@Column(length=3)
	@TFieldImportRule(required = false, 
	description = "#{label.currency.code}", column = "currency_code", 
	example="BRL", maxLength=3)
	private String currencyCode;
	
	@Column(length=60)
	@TFieldImportRule(required = false, 
	description = "#{label.currency.name}", column = "currency_name", 
	example="Brazilian Real", maxLength=60)
	private String currencyName;
	
	@Column(length=2)
	@TFieldImportRule(required = false, 
	description = "#{label.lang.code}", column = "lang_code", 
	example="PT", caseSensitive=TCaseSensitive.UPPER, maxLength=2)
	private String langCode;
	
	@Column(length=60)
	@TFieldImportRule(required = false, 
	description = "#{label.lang.name}", column = "lang_name", 
	example="Portuguese", caseSensitive=TCaseSensitive.UPPER, maxLength=60)
	private String langName;
	
	@Column(length=2)
	@TFieldImportRule(required = false, 
	description = "#{label.cctld}", column = "cctld", 
	example="br", caseSensitive=TCaseSensitive.LOWER, maxLength=2)
	private String cctld;
	
	@Column(length=20)
	@TFieldImportRule(required = false, 
	description = "Latitude", column = "latitude", 
	example="-14.235004",  maxLength=20)
	private String latitude;
	
	@Column(length=20)
	@TFieldImportRule(required = false, 
	description = "Longitude", column = "longitude", 
	example="-51.92528",  maxLength=20)
	private String longitude;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="image_id")
	private TFileEntity flag;

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getNumericCode() {
		return numericCode;
	}

	public void setNumericCode(Long numericCode) {
		this.numericCode = numericCode;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public Long getTotalArea() {
		return totalArea;
	}

	public void setTotalArea(Long totalArea) {
		this.totalArea = totalArea;
	}

	public Long getPopulation() {
		return population;
	}

	public void setPopulation(Long population) {
		this.population = population;
	}

	public Integer getIddCode() {
		return iddCode;
	}

	public void setIddCode(Integer iddCode) {
		this.iddCode = iddCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getLangName() {
		return langName;
	}

	public void setLangName(String langName) {
		this.langName = langName;
	}

	public String getCctld() {
		return cctld;
	}

	public void setCctld(String cctld) {
		this.cctld = cctld;
	}

	public TFileEntity getFlag() {
		return flag;
	}

	public void setFlag(TFileEntity flag) {
		this.flag = flag;
	}

	public String getIso2Code() {
		return iso2Code;
	}

	public void setIso2Code(String iso2Code) {
		this.iso2Code = iso2Code;
	}

	public String getIso3Code() {
		return iso3Code;
	}

	public void setIso3Code(String iso3Code) {
		this.iso3Code = iso3Code;
	}

	public String getPopulationName() {
		return populationName;
	}

	public void setPopulationName(String populationName) {
		this.populationName = populationName;
	}

}

/**
 * 
 */
package org.tedros.extension.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.tedros.common.model.TFileEntity;
import org.tedros.extension.domain.DomainSchema;
import org.tedros.extension.domain.DomainTables;
import org.tedros.server.annotation.TCaseSensitive;
import org.tedros.server.annotation.TField;
import org.tedros.server.annotation.TFileType;
import org.tedros.server.annotation.TImportInfo;
import org.tedros.server.entity.TVersionEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.country, schema = DomainSchema.schema, 
uniqueConstraints= {@UniqueConstraint(name="countryCodeUnIdx", columnNames = { "iso2Code" })} )
@TImportInfo(description = "#{country.import.rule.desc}", 
fileType = { TFileType.CSV, TFileType.XLS })
public class Country extends TVersionEntity {

	private static final long serialVersionUID = 1L;

	@Column(length=2, nullable=false)
	@TField(required = true, 
	label = "#{label.country.code} (ISO2)", column = "iso2", 
	example="BR", caseSensitive=TCaseSensitive.UPPER, maxLength=2)
	private String iso2Code;
	
	@Column(length=60, nullable=false)
	@TField(required = true, 
	label = "#{label.name}", column = "name", 
	example="Brazil", maxLength=60)
	private String name;
	
	@Column(length=3, nullable=false)
	@TField(required = true, 
	label = "#{label.country.code} (ISO3)", column = "iso3", 
	example="BRA", caseSensitive=TCaseSensitive.UPPER, maxLength=3)
	private String iso3Code;
	
	@Column
	@TField(required = false, 
	label = "#{label.numeric.code}", column = "numeric_code", 
	example="76", numberType=Long.class)
	private Long numericCode;
	
	@Column(length=120, nullable=false)
	@TField(required = true, 
	label = "#{label.capital}", column = "capital", 
	example="Brasilia", maxLength=120)
	private String capital;
	
	@Column(length=60)
	@TField(required = false, 
	label = "#{label.population.name}", column = "population_name", 
	example="Brazilians", maxLength=60)
	private String populationName;
	
	@Column
	@TField(required = false, 
	label = "#{label.total.area}", column = "total_area", 
	example="8514877", numberType=Long.class)
	private Long totalArea;
	
	@Column
	@TField(required = false, 
	label = "#{label.population}", column = "population", 
	example="212559417", numberType=Long.class)
	private Long population;
	
	@Column
	@TField(required = false, 
	label = "#{label.idd.code}", column = "idd_code", 
	example="55", numberType=Integer.class)
	private Integer iddCode;
	
	@Column(length=3)
	@TField(required = false, 
	label = "#{label.currency.code}", column = "currency_code", 
	example="BRL", maxLength=3)
	private String currencyCode;
	
	@Column(length=60)
	@TField(required = false, 
	label = "#{label.currency.name}", column = "currency_name", 
	example="Brazilian Real", maxLength=60)
	private String currencyName;
	
	@Column(length=2)
	@TField(required = false, 
	label = "#{label.lang.code}", column = "lang_code", 
	example="PT", caseSensitive=TCaseSensitive.UPPER, maxLength=2)
	private String langCode;
	
	@Column(length=60)
	@TField(required = false, 
	label = "#{label.lang.name}", column = "lang_name", 
	example="Portuguese", caseSensitive=TCaseSensitive.UPPER, maxLength=60)
	private String langName;
	
	@Column(length=2)
	@TField(required = false, 
	label = "#{label.cctld}", column = "cctld", 
	example="br", caseSensitive=TCaseSensitive.LOWER, maxLength=2)
	private String cctld;
	
	@Column(length=20)
	@TField(required = false, 
	label = "Latitude", column = "latitude", 
	example="-14.235004",  maxLength=20)
	private String latitude;
	
	@Column(length=20)
	@TField(required = false, 
	label = "Longitude", column = "longitude", 
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

	@Override
	public String toString() {
		return name!=null ? name : "";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((capital == null) ? 0 : capital.hashCode());
		result = prime * result + ((cctld == null) ? 0 : cctld.hashCode());
		result = prime * result + ((currencyCode == null) ? 0 : currencyCode.hashCode());
		result = prime * result + ((currencyName == null) ? 0 : currencyName.hashCode());
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result + ((iddCode == null) ? 0 : iddCode.hashCode());
		result = prime * result + ((iso2Code == null) ? 0 : iso2Code.hashCode());
		result = prime * result + ((iso3Code == null) ? 0 : iso3Code.hashCode());
		result = prime * result + ((langCode == null) ? 0 : langCode.hashCode());
		result = prime * result + ((langName == null) ? 0 : langName.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((numericCode == null) ? 0 : numericCode.hashCode());
		result = prime * result + ((population == null) ? 0 : population.hashCode());
		result = prime * result + ((populationName == null) ? 0 : populationName.hashCode());
		result = prime * result + ((totalArea == null) ? 0 : totalArea.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (capital == null) {
			if (other.capital != null)
				return false;
		} else if (!capital.equals(other.capital))
			return false;
		if (cctld == null) {
			if (other.cctld != null)
				return false;
		} else if (!cctld.equals(other.cctld))
			return false;
		if (currencyCode == null) {
			if (other.currencyCode != null)
				return false;
		} else if (!currencyCode.equals(other.currencyCode))
			return false;
		if (currencyName == null) {
			if (other.currencyName != null)
				return false;
		} else if (!currencyName.equals(other.currencyName))
			return false;
		if (flag == null) {
			if (other.flag != null)
				return false;
		} else if (!flag.equals(other.flag))
			return false;
		if (iddCode == null) {
			if (other.iddCode != null)
				return false;
		} else if (!iddCode.equals(other.iddCode))
			return false;
		if (iso2Code == null) {
			if (other.iso2Code != null)
				return false;
		} else if (!iso2Code.equals(other.iso2Code))
			return false;
		if (iso3Code == null) {
			if (other.iso3Code != null)
				return false;
		} else if (!iso3Code.equals(other.iso3Code))
			return false;
		if (langCode == null) {
			if (other.langCode != null)
				return false;
		} else if (!langCode.equals(other.langCode))
			return false;
		if (langName == null) {
			if (other.langName != null)
				return false;
		} else if (!langName.equals(other.langName))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (numericCode == null) {
			if (other.numericCode != null)
				return false;
		} else if (!numericCode.equals(other.numericCode))
			return false;
		if (population == null) {
			if (other.population != null)
				return false;
		} else if (!population.equals(other.population))
			return false;
		if (populationName == null) {
			if (other.populationName != null)
				return false;
		} else if (!populationName.equals(other.populationName))
			return false;
		if (totalArea == null) {
			if (other.totalArea != null)
				return false;
		} else if (!totalArea.equals(other.totalArea))
			return false;
		return true;
	}

}

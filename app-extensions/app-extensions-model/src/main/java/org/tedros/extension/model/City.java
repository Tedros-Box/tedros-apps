/**
 * 
 */
package org.tedros.extension.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
@Table(name = DomainTables.city, schema = DomainSchema.schema)
@TImportInfo(description = "#{city.import.rule.desc}", 
fileType = { TFileType.CSV, TFileType.XLS })
public class City extends TVersionEntity {

	private static final long serialVersionUID = 1L;

	@Column(length=2, nullable=false)
	@TField(required = true, 
	label = "#{label.country.code} (ISO2)", column = "country_iso2", 
	example="BR", caseSensitive=TCaseSensitive.UPPER, maxLength=2)
	private String countryIso2Code;
	
	@Column(length=120, nullable=false)
	@TField(required = true, 
	label = "#{label.name}", column = "name", 
	example="São Paulo", maxLength=120)
	private String name;
	
	@Column(length=10, nullable=true)
	@TField(required = false, 
	label = "#{label.capital}", column = "capital", 
	example="admin",possibleValues= {"admin","primary","minor"},  maxLength=10)
	private String capital;
	

	@Column(length=120, nullable=true)
	@TField(required = false, 
	label = "#{label.admin.area}", column = "admin_name", 
	example="São Paulo",  maxLength=120)
	private String adminArea;
	
	@Column
	@TField(required = false, 
	label = "#{label.population}", column = "population", 
	example="212559417", numberType=Long.class)
	private Long population;
	
	@Column(length=20)
	@TField(required = false, 
	label = "Latitude", column = "lat", 
	example="-14.235004",  maxLength=20)
	private String latitude;
	
	@Column(length=20)
	@TField(required = false, 
	label = "Longitude", column = "lng", 
	example="-51.92528",  maxLength=20)
	private String longitude;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public Long getPopulation() {
		return population;
	}

	public void setPopulation(Long population) {
		this.population = population;
	}

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

	public String getCountryIso2Code() {
		return countryIso2Code;
	}

	public void setCountryIso2Code(String countryIso2Code) {
		this.countryIso2Code = countryIso2Code;
	}

	public String getAdminArea() {
		return adminArea;
	}

	public void setAdminArea(String adminArea) {
		this.adminArea = adminArea;
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
		result = prime * result + ((adminArea == null) ? 0 : adminArea.hashCode());
		result = prime * result + ((capital == null) ? 0 : capital.hashCode());
		result = prime * result + ((countryIso2Code == null) ? 0 : countryIso2Code.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((population == null) ? 0 : population.hashCode());
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
		City other = (City) obj;
		if (adminArea == null) {
			if (other.adminArea != null)
				return false;
		} else if (!adminArea.equals(other.adminArea))
			return false;
		if (capital == null) {
			if (other.capital != null)
				return false;
		} else if (!capital.equals(other.capital))
			return false;
		if (countryIso2Code == null) {
			if (other.countryIso2Code != null)
				return false;
		} else if (!countryIso2Code.equals(other.countryIso2Code))
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
		if (population == null) {
			if (other.population != null)
				return false;
		} else if (!population.equals(other.population))
			return false;
		return true;
	}

}

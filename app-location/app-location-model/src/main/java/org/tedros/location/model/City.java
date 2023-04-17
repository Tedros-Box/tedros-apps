/**
 * 
 */
package org.tedros.location.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.tedros.location.domain.DomainSchema;
import org.tedros.location.domain.DomainTables;
import org.tedros.server.annotation.TCaseSensitive;
import org.tedros.server.annotation.TImportInfo;
import org.tedros.server.annotation.TField;
import org.tedros.server.annotation.TFileType;
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

}

/**
 * 
 */
package org.tedros.location.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.tedros.location.domain.DomainSchema;
import org.tedros.location.domain.DomainTables;

import org.tedros.server.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.address, schema = DomainSchema.schema)
public class Address extends TEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="street_id", nullable=false)
	private StreetType streetType;
	
	@Column(length=120, nullable=false)
	private String publicPlace;
	
	@Column(length=120, nullable=true)
	private String complement;

	@Column(length=120, nullable=true)
	private String neighborhood;
	
	@ManyToOne
	@JoinColumn(name="country_id", nullable=false)
	private Country country;
	
	@ManyToOne
	@JoinColumn(name="admarea_id", nullable=true)
	private AdminArea adminArea;
	
	@ManyToOne
	@JoinColumn(name="city_id", nullable=true)
	private City city;
	
	@Column(length=20, nullable=true)
	private String code;
	
	@Column(length=15)
	private String latitude;

	@Column(length=15)
	private String logintude;

	public StreetType getStreetType() {
		return streetType;
	}

	public void setStreetType(StreetType streetType) {
		this.streetType = streetType;
	}

	public String getPublicPlace() {
		return publicPlace;
	}

	public void setPublicPlace(String publicPlace) {
		this.publicPlace = publicPlace;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public AdminArea getAdminArea() {
		return adminArea;
	}

	public void setAdminArea(AdminArea adminArea) {
		this.adminArea = adminArea;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return String.format(
				"%s %s, %s, %s - %s %s %s %s",
				streetType, (publicPlace!=null?publicPlace:""), (complement!=null?complement:""), 
				(neighborhood!=null?neighborhood:""), (country!=null?country:""), 
				(adminArea!=null?adminArea:""), (city!=null?city:""), (code!=null?code:""));
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLogintude() {
		return logintude;
	}

	public void setLogintude(String logintude) {
		this.logintude = logintude;
	}
	
	
	
}

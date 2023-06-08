/**
 * 
 */
package org.tedros.extension.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.tedros.extension.domain.DomainSchema;
import org.tedros.extension.domain.DomainTables;
import org.tedros.server.entity.TVersionEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.address, schema = DomainSchema.schema)
public class Address extends TVersionEntity {

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
	

	public Address() {
		
	}

	public Address(Address address) {
		this.streetType = address.streetType;
		this.publicPlace = address.publicPlace;
		this.complement = address.complement;
		this.neighborhood = address.neighborhood;
		this.country = address.country;
		this.adminArea = address.adminArea;
		this.city = address.city;
		this.code = address.code;
		this.latitude = address.latitude;
		this.logintude = address.logintude;
	}

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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((adminArea == null) ? 0 : adminArea.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((complement == null) ? 0 : complement.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((logintude == null) ? 0 : logintude.hashCode());
		result = prime * result + ((neighborhood == null) ? 0 : neighborhood.hashCode());
		result = prime * result + ((publicPlace == null) ? 0 : publicPlace.hashCode());
		result = prime * result + ((streetType == null) ? 0 : streetType.hashCode());
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
		Address other = (Address) obj;
		if (adminArea == null) {
			if (other.adminArea != null)
				return false;
		} else if (!adminArea.equals(other.adminArea))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (complement == null) {
			if (other.complement != null)
				return false;
		} else if (!complement.equals(other.complement))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (logintude == null) {
			if (other.logintude != null)
				return false;
		} else if (!logintude.equals(other.logintude))
			return false;
		if (neighborhood == null) {
			if (other.neighborhood != null)
				return false;
		} else if (!neighborhood.equals(other.neighborhood))
			return false;
		if (publicPlace == null) {
			if (other.publicPlace != null)
				return false;
		} else if (!publicPlace.equals(other.publicPlace))
			return false;
		if (streetType == null) {
			if (other.streetType != null)
				return false;
		} else if (!streetType.equals(other.streetType))
			return false;
		return true;
	}
	
	
	
}

/**
 * 
 */
package org.tedros.sample.entity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.tedros.extension.model.AdminArea;
import org.tedros.extension.model.City;
import org.tedros.extension.model.Country;
import org.tedros.sample.domain.DomainSchema;
import org.tedros.sample.domain.DomainTables;
import org.tedros.server.entity.TReceptiveEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Cacheable(false)
@Table(name = DomainTables.sampleb, schema = DomainSchema.schema)
public class SampleB extends TReceptiveEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6190572513740958774L;

	@Column
	private String name;
	
	@ManyToOne
	@JoinColumn(name="country_id")
	private Country country;
	
	@ManyToOne
	@JoinColumn(name="admarea_id")
	private AdminArea adminArea;
	
	@ManyToOne
	@JoinColumn(name="city_id")
	private City city;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * @return the adminArea
	 */
	public AdminArea getAdminArea() {
		return adminArea;
	}

	/**
	 * @param adminArea the adminArea to set
	 */
	public void setAdminArea(AdminArea adminArea) {
		this.adminArea = adminArea;
	}

	/**
	 * @return the city
	 */
	public City getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(City city) {
		this.city = city;
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
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		SampleB other = (SampleB) obj;
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
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return  (name != null ?  name : "");
	}
}

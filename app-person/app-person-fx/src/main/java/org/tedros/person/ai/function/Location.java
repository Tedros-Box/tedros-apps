/**
 * 
 */
package org.tedros.person.ai.function;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * @author Davis Gordon
 *
 */
@JsonClassDescription("Geographic filters for the search.")
public class Location {

    @JsonPropertyDescription("The neighborhood, district, or borough name.")
	private String neighborhood;
	
	@JsonPropertyDescription("The 2-letter Country ISO Code (e.g., 'US', 'BR', 'DE'). Do not use full country names.")
	private String countryIso2Code;
	
	@JsonPropertyDescription("The administrative area, state, province, or region.")
	private String adminArea;
	
	@JsonPropertyDescription("The city or municipality name.")
	private String city;
	
	/**
	 * 
	 */
	public Location() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the neighborhood
	 */
	public String getNeighborhood() {
		return neighborhood;
	}

	/**
	 * @param neighborhood the neighborhood to set
	 */
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	/**
	 * @return the countryIso2Code
	 */
	public String getCountryIso2Code() {
		return countryIso2Code;
	}

	/**
	 * @param countryIso2Code the countryIso2Code to set
	 */
	public void setCountryIso2Code(String countryIso2Code) {
		this.countryIso2Code = countryIso2Code;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the adminArea
	 */
	public String getAdminArea() {
		return adminArea;
	}

	/**
	 * @param adminArea the adminArea to set
	 */
	public void setAdminArea(String adminArea) {
		this.adminArea = adminArea;
	}

}

/**
 * 
 */
package com.tedros.location.report.model;

import com.tedros.ejb.base.model.TReportModel;
import com.tedros.location.model.AdminArea;
import com.tedros.location.model.City;
import com.tedros.location.model.Country;
import com.tedros.location.model.PlaceType;
import com.tedros.location.model.StreetType;

/**
 * @author Davis Gordon
 *
 */
public class PlaceReportModel extends TReportModel<PlaceItemModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5278175154749892227L;

	private String title;
	
	private PlaceType type;
	
	private Country country;
	
	private AdminArea adminArea;
	
	private City city;
	
	private String code;
	
	private StreetType streetType;
	
	private String publicPlace;
	/**
	 * 
	 */
	public PlaceReportModel() {
		// TODO Auto-generated constructor stub
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public PlaceType getType() {
		return type;
	}
	public void setType(PlaceType type) {
		this.type = type;
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

}

/**
 * 
 */
package org.tedros.extension.report.model;

import org.tedros.extension.model.Place;
import org.tedros.server.model.ITReportItemModel;

/**
 * @author Davis Gordon
 *
 */
public class PlaceItemModel implements ITReportItemModel<Place> {

	private static final long serialVersionUID = 2413643979622464683L;

	private String title;
	
	private String type;
	
	private String country;
	
	private String address;
	
	public String contacts;
	
	public String description;
	
	private Place item;
	
	/**
	 * 
	 */
	public PlaceItemModel() {
	}

	public PlaceItemModel(Place p) {
		this.item = p;
		this.title = p.getTitle();
		this.type = p.getType().getName();
		this.country = p.getAddress().getCountry().getName();
		this.address = p.getAddress().toString();
		this.contacts = p.getAllContacts();
		this.description = p.getDescription();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Place getModel() {
		return item;
	}

}

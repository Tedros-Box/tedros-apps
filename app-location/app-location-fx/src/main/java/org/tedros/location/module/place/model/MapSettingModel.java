/**
 * 
 */
package com.tedros.location.module.place.model;

import java.util.Properties;

import com.tedros.ejb.base.model.ITModel;
import com.tedros.location.resource.AppResource;

/**
 * @author Davis Gordon
 *
 */
public class MapSettingModel implements ITModel {

	private static final long serialVersionUID = -959756058000350000L;
	
	public static final String TYPE_GOOGLE="googlemaps";
	public static final String TYPE_MAPQUEST="mapquest";
	
	public static final String TYPE="type";
	public static final String MAPQUEST_KEY="mapquest.key";

	private String mapquestKey;
	
	private String mapType;
	
	/**
	 * 
	 */
	public MapSettingModel() {
		Properties p = AppResource.getSettings();
		this.mapType = p.getProperty(TYPE);
		this.mapquestKey = p.getProperty(MAPQUEST_KEY);
	}
	
	public boolean isGoogleType() {
		return mapType.equals(TYPE_GOOGLE);
	}
	
	public boolean isMapQuestType() {
		return mapType.equals(TYPE_MAPQUEST);
	}

	public String getMapquestKey() {
		return mapquestKey;
	}

	public void setMapquestKey(String mapquestKey) {
		this.mapquestKey = mapquestKey;
	}

	public String getMapType() {
		return mapType;
	}

	public void setMapType(String mapType) {
		this.mapType = mapType;
	}

}

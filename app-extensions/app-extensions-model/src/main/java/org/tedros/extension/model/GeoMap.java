package org.tedros.extension.model;

import org.apache.commons.lang3.StringUtils;

public interface GeoMap {
	
	static final String NULL_PARAM = "'null'";

	GeoLocation getGeoLocation();
	
	static String getLocOf(GeoMap g) {
		if(g instanceof Country) return "'country'";
		if(g instanceof City) return "'city'";
		if(g instanceof Address) return "'address'";
		return "''";
	}
	
	static GeoLocation process(Country m) {
		
		String lat = NULL_PARAM;
		String lon = NULL_PARAM;
		String co = NULL_PARAM;
		String st = NULL_PARAM;
		String ci = NULL_PARAM;
		String str = NULL_PARAM;
		String cp = NULL_PARAM;
		co = m.getName()!=null ? "'"+m.getName()+"'" : co;
		lat = !StringUtils.isBlank(m.getLatitude()) ? "'"+m.getLatitude()+"'" : lat;
		lon = !StringUtils.isBlank(m.getLongitude()) ? "'"+m.getLongitude()+"'" : lon;
		
		return new GeoLocation(lat, lon, co, st, ci, str, cp);
	}
	
	static GeoLocation process(AdminArea m) {
		
		String lat = NULL_PARAM;
		String lon = NULL_PARAM;
		String co = NULL_PARAM;
		String st = NULL_PARAM;
		String ci = NULL_PARAM;
		String str = NULL_PARAM;
		String cp = NULL_PARAM;
		
		co = m.getCountryIso2Code()!=null ? "'"+m.getCountryIso2Code()+"'" : co;
		st = m.getName()!=null ? "'"+m.getName()+"'" : st;
		
		return new GeoLocation(lat, lon, co, st, ci, str, cp);
	}
	
	static GeoLocation process(City m) {
		
		String lat = NULL_PARAM;
		String lon = NULL_PARAM;
		String co = NULL_PARAM;
		String st = NULL_PARAM;
		String ci = NULL_PARAM;
		String str = NULL_PARAM;
		String cp = NULL_PARAM;
		
		co = m.getCountryIso2Code()!=null ? "'"+m.getCountryIso2Code()+"'" : co;
		st = m.getAdminArea()!=null ? "'"+m.getAdminArea()+"'" : st;
		ci = m.getName()!=null ? "'"+m.getName()+"'" : ci;
		lat = m.getLatitude()!=null ? "'"+m.getLatitude()+"'" : lat;
		lon = m.getLongitude()!=null ? "'"+m.getLongitude()+"'" : lon;
		
		return new GeoLocation(lat, lon, co, st, ci, str, cp);
	}
	
	static GeoLocation process(Address m) {
		
		String lat = NULL_PARAM;
		String lon = NULL_PARAM;
		String co = NULL_PARAM;
		String st = NULL_PARAM;
		String ci = NULL_PARAM;
		String sttp = null;
		String pp = null;
		String str = NULL_PARAM;
		String cp = NULL_PARAM;
		
		co = m.getCountry()!=null ? "'"+m.getCountry().getName()+"'" : co;
		st = m.getAdminArea()!=null ? "'"+m.getAdminArea().getName()+"'" : st;
		ci = m.getCity()!=null ? "'"+m.getCity().getName()+"'" : ci;
		sttp = m.getStreetType()!=null ? "'"+m.getStreetType().getName()+" " : sttp;
		pp = m.getPublicPlace()!=null ? m.getPublicPlace()+"'" : pp;
		str = !NULL_PARAM.equals(co)  && !NULL_PARAM.equals(st) && !NULL_PARAM.equals(ci) 
				&& sttp!=null && pp!=null ? sttp+pp : str;
		cp = m.getCode()!=null ? "'"+m.getCode()+"'" :cp;
		
		lat = m.getLatitude()!=null ? "'"+m.getLatitude()+"'" : lat;
		lon = m.getLongitude()!=null ? "'"+m.getLongitude()+"'" : lon;
		
		return new GeoLocation(lat, lon, co, st, ci, str, cp);
	}

}
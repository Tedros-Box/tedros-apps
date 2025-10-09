/**
 * 
 */
package org.tedros.extension.module.setting;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.extension.model.AddressMV;
import org.tedros.extension.model.City;
import org.tedros.extension.model.Coordinated;
import org.tedros.extension.model.GeoLocation;
import org.tedros.extension.model.GeoMap;
import org.tedros.extension.module.adminArea.model.AdminAreaMV;
import org.tedros.extension.module.city.model.CityMV;
import org.tedros.extension.module.country.model.CountryMV;
import org.tedros.extension.module.place.model.MapSettingModel;
import org.tedros.fx.form.TSetting;
import org.tedros.fx.model.TEntityModelView;

import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

/**
 * @author Davis Gordon
 *
 */
public class MapSetting extends TSetting {

	private MapSettingModel msm = new MapSettingModel();
	
	private boolean breakload = false;
		
	public MapSetting(ITComponentDescriptor descriptor) {
		super(descriptor);
	}
	
	@Override
	public void run() {
		getForm().gettObjectRepository().add("webviewBridge", this);
		WebEngine we = getWebEngine();
		we.setJavaScriptEnabled(true);
		
		we.getLoadWorker().stateProperty().addListener((a, b, n)->{
			if(State.SUCCEEDED==n && !breakload) {
				
				if(msm.isMapQuestType()) {
					JSObject window = (JSObject) we.executeScript("window");
					window.setMember("tedros", this);
					String key = msm.getMapquestKey();
					getWebEngine().executeScript("buildMap('"+key+"')");
				}
				
				findOnMap();
				
				listenControls();
				breakload=true;
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void listenControls() {
		
		TEntityModelView<?> mv = getModelView();
		
		if(mv instanceof AddressMV) {
			ComboBox co = super.getControl("country");
			ComboBox aa = super.getControl("adminArea");
			ComboBox ci = super.getControl("city");
			ComboBox st = super.getControl("streetType");
			TextField pp = super.getControl("publicPlace");
			TextField pc = super.getControl("code");
			TextField lt = super.getControl("latitude");
			TextField lg = super.getControl("longitude");
			EventHandler<ActionEvent> ae = e->{
				findOnMap();
			};
			pc.setOnAction(ae);
			pp.setOnAction(ae);
			lt.setOnAction(ae);
			lg.setOnAction(ae);
			
			ChangeListener chl = (x,o,n)->{
				findOnMap();
			};
			st.getSelectionModel().selectedItemProperty().addListener(chl);
			ci.getSelectionModel().selectedItemProperty().addListener(chl);
			aa.getSelectionModel().selectedItemProperty().addListener(chl);
			co.getSelectionModel().selectedItemProperty().addListener(chl);
		}else if(mv instanceof CityMV) {
			TextField pp = super.getControl("countryIso2Code");
			TextField pc = super.getControl("adminArea");
			TextField aa = super.getControl("name");
			TextField lt = super.getControl("latitude");
			TextField lg = super.getControl("longitude");
			EventHandler<ActionEvent> ae = e->{
				findOnMap();
			};
			pc.setOnAction(ae);
			pp.setOnAction(ae);
			aa.setOnAction(ae);
			lt.setOnAction(ae);
			lg.setOnAction(ae);
		}else if(mv instanceof AdminAreaMV) {
			TextField pp = super.getControl("countryIso2Code");
			TextField aa = super.getControl("name");
			EventHandler<ActionEvent> ae = e->{
				findOnMap();
			};
			pp.setOnAction(ae);
			aa.setOnAction(ae);
		}else if(mv instanceof CountryMV) {
			TextField aa = super.getControl("name");
			TextField lt = super.getControl("latitude");
			TextField lg = super.getControl("longitude");
			EventHandler<ActionEvent> ae = e->{
				findOnMap();
			};
			aa.setOnAction(ae);
			lt.setOnAction(ae);
			lg.setOnAction(ae);
		}
		
	}

	private void findOnMap() {
		TEntityModelView<?> mv = getModelView();
		GeoMap g = (GeoMap) mv.getModel();
		GeoLocation r = g.getGeoLocation();
		String lat = r.latitude();
		String lng = r.longitude();
		
		if(msm.isMapQuestType()) {
			
			if(!lat.equals(GeoMap.NULL_PARAM) && !lng.equals(GeoMap.NULL_PARAM))
				getWebEngine().executeScript("setCoord("+GeoMap.getLocOf(g)+", "+lat+", "+lng+")");
			else			
				getWebEngine().executeScript("setAddress("+r.country()+", "+r.adminArea()+", "+r.city()+", "+r.street()+", "+r.complement()+")");
			
			getWebView().requestFocus();
		}
		
		if(msm.isGoogleType()) {
			
			if(!lat.equals(GeoMap.NULL_PARAM) && !lng.equals(GeoMap.NULL_PARAM))
				this.goToGoogleMaps(lat+","+lng);
			else {
				StringBuilder sb = new StringBuilder();
				if(!r.country().equals(GeoMap.NULL_PARAM)) sb.append(r.country()+" ");
				if(!r.adminArea().equals(GeoMap.NULL_PARAM)) sb.append(r.adminArea()+" ");
				if(!r.city().equals(GeoMap.NULL_PARAM)) sb.append(r.city()+" ");
				if(!r.street().equals(GeoMap.NULL_PARAM)) sb.append(r.street()+" ");
				
				if(!sb.toString().isEmpty())
					this.goToGoogleMaps(sb.toString());
			}
		}
	}
	
	public void setLatLng(String lat, String lng) {
		TEntityModelView<?> mv = getModelView();
		if(mv instanceof Coordinated) {
			Coordinated c = (Coordinated) mv;
			c.getLatitude().setValue(lat);
			c.getLongitude().setValue(lng);
		}
	}

	public void log(String text) {
		System.out.println(text); 
	}
	
	private void goToGoogleMaps(String location) {
		location = encode(location);
		String url = "https://www.google.com/maps/@?api=1&query="+location;
		getWebEngine().load(url);
	}
	
	private String encode(String value) {
	    try {
			return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return value;
		}
	}

	/**
	 * @return
	 */
	private WebEngine getWebEngine() {
		return getWebView().getEngine();
	}

	private WebView getWebView() {
		return super.getControl("webview");
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}

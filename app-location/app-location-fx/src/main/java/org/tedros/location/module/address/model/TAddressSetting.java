/**
 * 
 */
package com.tedros.location.module.address.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;

import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.form.TSetting;
import com.tedros.location.model.Address;
import com.tedros.location.module.place.model.MapSettingModel;

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
public class TAddressSetting extends TSetting {

	private static final String INIT_AT = "31.71438899216804,34.92498512349785";

	private static final String NULL_PARAM = "'null'";

	private MapSettingModel msm = new MapSettingModel();
	
	private boolean breakload = false;
		
	public TAddressSetting(TComponentDescriptor descriptor) {
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
				
				AddressMV mv = getModelView();
				Address m = mv.getModel();
				String lat = m.getLatitude();
				String lng = m.getLogintude();
				if(StringUtils.isNotBlank(lat) && StringUtils.isNotBlank(lng)) 
					goToCoord();
				else
					goToAddress();
				
				listenControls();
				breakload=true;
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void listenControls() {
		ComboBox co = super.getControl("country");
		ComboBox aa = super.getControl("adminArea");
		ComboBox ci = super.getControl("city");
		ComboBox st = super.getControl("streetType");
		TextField pp = super.getControl("publicPlace");
		TextField pc = super.getControl("code");
		TextField lt = super.getControl("latitude");
		TextField lg = super.getControl("logintude");
		EventHandler<ActionEvent> ael = e->{
			goToCoord();
		};
		lt.setOnAction(ael);
		lg.setOnAction(ael);
		
		EventHandler<ActionEvent> ae = e->{
			goToAddress();
		};
		pc.setOnAction(ae);
		pp.setOnAction(ae);
		
		ChangeListener chl = (x,o,n)->{
			goToAddress();
		};
		st.getSelectionModel().selectedItemProperty().addListener(chl);
		ci.getSelectionModel().selectedItemProperty().addListener(chl);
		aa.getSelectionModel().selectedItemProperty().addListener(chl);
		co.getSelectionModel().selectedItemProperty().addListener(chl);
	}

	private void goToAddress() {
		AddressMV mv = getModelView();
		Address m = mv.getModel();
		if(msm.isMapQuestType()) {
			String co = m.getCountry()!=null ? "'"+m.getCountry().getName()+"'" : NULL_PARAM;
			String st = m.getAdminArea()!=null ? "'"+m.getAdminArea().getName()+"'" : NULL_PARAM;
			String ci = m.getCity()!=null ? "'"+m.getCity().getName()+"'" : NULL_PARAM;
			String sttp = m.getStreetType()!=null ? "'"+m.getStreetType().getName()+" " : null;
			String pp = m.getPublicPlace()!=null ? m.getPublicPlace()+"'" : null;
			String str = !NULL_PARAM.equals(co)  && !NULL_PARAM.equals(st) && !NULL_PARAM.equals(ci) 
					&& sttp!=null && pp!=null ? sttp+pp : NULL_PARAM;
			String cp = m.getCode()!=null ? "'"+m.getCode()+"'" :NULL_PARAM;
			
			getWebEngine().executeScript("setAddress("+co+", "+st+", "+ci+", "+str+", "+cp+")");
			getWebView().requestFocus();
		}
		
		if(msm.isGoogleType()) {
			StringBuilder sb = new StringBuilder();
			if(m.getCountry()!=null) sb.append(m.getCountry().getName()+" ");
			if(m.getAdminArea()!=null) sb.append(m.getAdminArea().getName()+" ");
			if(m.getCity()!=null) sb.append(m.getCity().getName()+" ");
			if(m.getStreetType()!=null && m.getPublicPlace()!=null) 
				sb.append(m.getStreetType().getName()+" "+m.getPublicPlace()+" ");
			
			if(sb.toString().isEmpty())
				this.goToGoogleMaps(INIT_AT);
			else
				this.goToGoogleMaps(sb.toString());
		}
	}
	
	private void goToCoord() {
		AddressMV mv = getModelView();
		Address m = mv.getModel();
		String lat = m.getLatitude();
		String lng = m.getLogintude();
		if(StringUtils.isNotBlank(lat) && StringUtils.isNotBlank(lng)) {
			if(msm.isMapQuestType()) {
				getWebEngine().executeScript("setCoord('"+lat+"', '"+lng+"')");
				getWebView().requestFocus();
			}
			if(msm.isGoogleType()) {
				this.goToGoogleMaps(lat+","+lng);
			}
		}
	}
	
	public void setLatLng(String lat, String lng) {
		AddressMV mv = getModelView();
		mv.getLatitude().setValue(lat);
		mv.getLogintude().setValue(lng);
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
}

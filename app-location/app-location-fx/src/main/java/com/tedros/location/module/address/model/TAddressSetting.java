/**
 * 
 */
package com.tedros.location.module.address.model;

import org.apache.commons.lang3.StringUtils;

import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.form.TSetting;
import com.tedros.location.model.Address;

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

	private static final String NULL_PARAM = "'null'";

	public TAddressSetting(TComponentDescriptor descriptor) {
		super(descriptor);
	}
	
	@Override
	public void run() {
		getForm().gettObjectRepository().add("webviewBridge", this);
		WebEngine we = getWebEngine();
		we.setJavaScriptEnabled(true);
		we.getLoadWorker().stateProperty().addListener((a, b, n)->{
			if(State.SUCCEEDED==n) {
				JSObject window = (JSObject) we.executeScript("window");
				window.setMember("tedros", this);
				AddressMV mv = getModelView();
				Address m = mv.getModel();
				String lat = m.getLatitude();
				String lng = m.getLogintude();
				if(StringUtils.isNotBlank(lat) && StringUtils.isNotBlank(lng)) 
					goToCoord();
				else
					goToAddress();
				listenControls();
			}
		});
	}

	private void listenControls() {
		ComboBox co = super.getControl("country");
		ComboBox aa = super.getControl("adminArea");
		ComboBox ci = super.getControl("city");
		ComboBox st = super.getControl("streetType");
		TextField pp = super.getControl("plubicPlace");
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
		String lat = m.getLatitude();
		String lng = m.getLogintude();
		String co = m.getCountry()!=null ? "'"+m.getCountry().getName()+"'" : NULL_PARAM;
		String st = m.getAdminArea()!=null ? "'"+m.getAdminArea().getName()+"'" : NULL_PARAM;
		String ci = m.getCity()!=null ? "'"+m.getCity().getName()+"'" : NULL_PARAM;
		String sttp = m.getStreetType()!=null ? "'"+m.getStreetType().getName()+" " : null;
		String pp = m.getPlubicPlace()!=null ? m.getPlubicPlace()+"'" : null;
		String str = !NULL_PARAM.equals(co)  && !NULL_PARAM.equals(st) && !NULL_PARAM.equals(ci) 
				&& sttp!=null && pp!=null ? sttp+pp : NULL_PARAM;
		String cp = m.getCode()!=null ? "'"+m.getCode()+"'" :NULL_PARAM;
		getWebEngine().executeScript("setAddress("+co+", "+st+", "+ci+", "+str+", "+cp+")");
		getWebView().requestFocus();
	}
	
	private void goToCoord() {
		AddressMV mv = getModelView();
		Address m = mv.getModel();
		String lat = m.getLatitude();
		String lng = m.getLogintude();
		if(StringUtils.isNotBlank(lat) && StringUtils.isNotBlank(lng)) {
			getWebEngine().executeScript("setCoord('"+lat+"', '"+lng+"')");
			getWebView().requestFocus();
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

/**
 * 
 */
package com.tedros.location.module.address.model;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.form.TSetting;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TEditModalPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.scene.web.TWebEngine;
import com.tedros.fxapi.annotation.scene.web.TWebView;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.location.annotation.TAdminAreaComboBox;
import com.tedros.location.annotation.TCityComboBox;
import com.tedros.location.annotation.TCountryComboBox;
import com.tedros.location.domain.DomainApp;
import com.tedros.location.model.Address;
import com.tedros.location.model.AdminArea;
import com.tedros.location.model.City;
import com.tedros.location.model.Country;
import com.tedros.location.model.StreetType;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TEditModalPresenter()
@TSetting(TAddressSetting.class)
@TEjbService(serviceName = "IAddressControllerRemote", model=Address.class)
@TSecurity(	id=DomainApp.ADDRESS_FORM_ID, 
appName = "#{app.location.name}", moduleName = "#{module.administrative}", viewName = "#{view.address}",
allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
				TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})

public class AddressMV extends TEntityModelView<Address> {


	private SimpleLongProperty id;
	
	/*@TTabPane(tabs = { @TTab(text = "#{label.main.data}", 
			content = @TContent(detailForm=@TDetailForm(fields = {"country", "streetType", "complement"}))),
			@TTab(text = "#{label.map}", 
				content = @TContent(detailForm=@TDetailForm(fields = {"latitude", "webview"}))) 
	})*/
	private SimpleStringProperty display;
	

	@TReaderHtml
	@TLabel(text="#{label.country}")
	@TCountryComboBox(firstItemTex="#{label.select}", required=true)
	@THBox(	pane=@TPane(children={"country", "adminArea", "city"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="country", priority=Priority.ALWAYS), 
			@TPriority(field="adminArea", priority=Priority.ALWAYS), 
			@TPriority(field="city", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<Country> country;
	
	@TReaderHtml
	@TLabel(text="#{label.admin.area}")
	@TAdminAreaComboBox(firstItemTex="#{label.select}", countryField="country", required=false)
	private SimpleObjectProperty<AdminArea> adminArea;
	
	@TReaderHtml
	@TLabel(text="#{label.city}")
	@TCityComboBox(firstItemTex="#{label.select}", countryField="country", adminAreaField="adminArea",  required=false)
	private SimpleObjectProperty<City> city;
	
	
	@TReaderHtml
	@TLabel(text="#{label.street.type}")
	@TComboBoxField(firstItemTex="#{label.select}", required=true,
		optionsList=@TOptionsList(serviceName = "IStreetTypeControllerRemote", 
		optionModelViewClass=StreetTypeMV.class,
		entityClass=StreetType.class))
	@THBox(	pane=@TPane(children={"streetType", "plubicPlace", "complement", "neighborhood", "code"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="streetType", priority=Priority.SOMETIMES), 
			@TPriority(field="plubicPlace", priority=Priority.ALWAYS),
			@TPriority(field="complement", priority=Priority.ALWAYS), 
			@TPriority(field="neighborhood", priority=Priority.ALWAYS), 
			@TPriority(field="code", priority=Priority.SOMETIMES)}))
	private SimpleObjectProperty<StreetType> streetType;
	
	@TReaderHtml
	@TLabel(text="#{label.public.place}")
	@TTextField(maxLength=120, required = true, 
		control=@TControl(tooltip="#{text.mapview.press.enter}", parse = true), 
		node=@TNode(requestFocus=true, parse = true))
	private SimpleStringProperty plubicPlace;
	
	@TReaderHtml
	@TLabel(text="#{label.complement}")
	@TTextField(maxLength=120)
	private SimpleStringProperty complement;

	@TReaderHtml
	@TLabel(text="#{label.neighborhood}")
	@TTextField(maxLength=120)
	private SimpleStringProperty neighborhood;
	
	@TReaderHtml
	@TLabel(text="#{label.address.code}")
	@TTextField(maxLength=20, 
	control=@TControl(tooltip="#{text.mapview.press.enter}", parse = true),
	textInputControl=@TTextInputControl(promptText="#{text.address.code}", parse = true))
	private SimpleStringProperty code;
	
	@TReaderHtml
	@TLabel(text="Latitude")
	@TTextField(maxLength=15, 
			control=@TControl(tooltip="#{text.mapview.press.enter}", parse = true))
	@THBox(	pane=@TPane(children={"latitude", "logintude"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="latitude", priority=Priority.ALWAYS), 
			@TPriority(field="logintude", priority=Priority.ALWAYS)}))
	private SimpleStringProperty latitude;
	
	@TReaderHtml
	@TLabel(text="Logintude")
	@TTextField(maxLength=15, 
			control=@TControl(tooltip="#{text.mapview.press.enter}", parse = true))
	private SimpleStringProperty logintude;
	
	/*@THyperlinkField(labeled = @TLabeled(text="View On Map", parse = true))
	private SimpleStringProperty viewMap;*/
	
	@TWebView(prefHeight=300,
			engine=@TWebEngine(//load="http://localhost:8081/editor-web-webapp/story/edit.html",
			load="file:C:/Users/Davis Gordon/.tedros/MODULE/geolocation/location.html"))
	private SimpleStringProperty webview;
	
	public AddressMV(Address entity) {
		super(entity);
		this.display = new SimpleStringProperty();
		this.formatFieldsToDisplay("%s %s %s", this.streetType, this.plubicPlace, this.complement);
	}
	

	@Override
	public void reload(Address e) {
		super.reload(e);
		this.formatFieldsToDisplay("%s %s %s", this.streetType, this.plubicPlace, this.complement);
	}
	

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleObjectProperty<StreetType> getStreetType() {
		return streetType;
	}

	public void setStreetType(SimpleObjectProperty<StreetType> streetType) {
		this.streetType = streetType;
	}

	public SimpleStringProperty getPlubicPlace() {
		return plubicPlace;
	}

	public void setPlubicPlace(SimpleStringProperty plubicPlace) {
		this.plubicPlace = plubicPlace;
	}

	public SimpleStringProperty getComplement() {
		return complement;
	}

	public void setComplement(SimpleStringProperty complement) {
		this.complement = complement;
	}

	public SimpleStringProperty getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(SimpleStringProperty neighborhood) {
		this.neighborhood = neighborhood;
	}

	public SimpleStringProperty getCode() {
		return code;
	}

	public void setCode(SimpleStringProperty code) {
		this.code = code;
	}

	public SimpleObjectProperty<Country> getCountry() {
		return country;
	}

	public void setCountry(SimpleObjectProperty<Country> country) {
		this.country = country;
	}

	public SimpleObjectProperty<AdminArea> getAdminArea() {
		return adminArea;
	}

	public void setAdminArea(SimpleObjectProperty<AdminArea> adminArea) {
		this.adminArea = adminArea;
	}

	public SimpleObjectProperty<City> getCity() {
		return city;
	}

	public void setCity(SimpleObjectProperty<City> city) {
		this.city = city;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return display;
	}


	public SimpleStringProperty getDisplay() {
		return display;
	}


	public void setDisplay(SimpleStringProperty display) {
		this.display = display;
	}


	public SimpleStringProperty getLatitude() {
		return latitude;
	}


	public void setLatitude(SimpleStringProperty latitude) {
		this.latitude = latitude;
	}


	public SimpleStringProperty getLogintude() {
		return logintude;
	}


	public void setLogintude(SimpleStringProperty logintude) {
		this.logintude = logintude;
	}


	public SimpleStringProperty getWebview() {
		return webview;
	}


	public void setWebview(SimpleStringProperty webview) {
		this.webview = webview;
	}

/*
	public SimpleStringProperty getViewMap() {
		return viewMap;
	}


	public void setViewMap(SimpleStringProperty viewMap) {
		this.viewMap = viewMap;
	}*/

}

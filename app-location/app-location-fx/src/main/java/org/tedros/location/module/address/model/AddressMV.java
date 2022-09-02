/**
 * 
 */
package org.tedros.location.module.address.model;

import static org.tedros.core.annotation.security.TAuthorizationType.DELETE;
import static org.tedros.core.annotation.security.TAuthorizationType.EDIT;
import static org.tedros.core.annotation.security.TAuthorizationType.NEW;
import static org.tedros.core.annotation.security.TAuthorizationType.SAVE;
import static org.tedros.core.annotation.security.TAuthorizationType.VIEW_ACCESS;

import org.tedros.core.annotation.security.TSecurity;
import org.tedros.ejb.controller.IAddressController;
import org.tedros.ejb.controller.IStreetTypeController;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TOptionsList;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.control.TTextInputControl;
import org.tedros.fx.annotation.form.TSetting;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TEditModalPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.web.TWebEngine;
import org.tedros.fx.annotation.scene.web.TWebView;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.location.LocatKey;
import org.tedros.location.annotation.TAdminAreaComboBox;
import org.tedros.location.annotation.TCityComboBox;
import org.tedros.location.annotation.TCountryComboBox;
import org.tedros.location.domain.DomainApp;
import org.tedros.location.model.Address;
import org.tedros.location.model.AdminArea;
import org.tedros.location.model.City;
import org.tedros.location.model.Country;
import org.tedros.location.model.StreetType;
import org.tedros.location.start.TConstant;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TEditModalPresenter
@TSetting(TAddressSetting.class)
@TEjbService(serviceName = IAddressController.JNDI_NAME, model=Address.class)
@TSecurity(	id=DomainApp.ADDRESS_FORM_ID, appName = LocatKey.APP_LOCATION_NAME,
moduleName = LocatKey.MODULE_ADMINISTRATIVE, viewName = LocatKey.VIEW_ADDRESS,
allowedAccesses={VIEW_ACCESS, EDIT, SAVE, DELETE, NEW})
public class AddressMV extends TEntityModelView<Address> {
	
	@TLabel(text=TUsualKey.COUNTRY)
	@TCountryComboBox(required=true)
	@THBox(	pane=@TPane(children={"country", "adminArea", "city"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="country", priority=Priority.ALWAYS), 
			@TPriority(field="adminArea", priority=Priority.ALWAYS), 
			@TPriority(field="city", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<Country> country;
	
	@TLabel(text=TUsualKey.ADMIN_AREA)
	@TAdminAreaComboBox(countryField="country", required=false)
	private SimpleObjectProperty<AdminArea> adminArea;
	
	@TLabel(text=TUsualKey.CITY)
	@TCityComboBox(countryField="country", adminAreaField="adminArea",  required=false)
	private SimpleObjectProperty<City> city;
	
	@TLabel(text=TUsualKey.STREET_TYPE)
	@TComboBoxField(required=true,
		optionsList=@TOptionsList(serviceName = IStreetTypeController.JNDI_NAME, 
		optionModelViewClass=StreetTypeMV.class,
		entityClass=StreetType.class))
	@THBox(	pane=@TPane(children={"streetType", "publicPlace", "complement", "neighborhood", "code"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="streetType", priority=Priority.SOMETIMES), 
			@TPriority(field="publicPlace", priority=Priority.ALWAYS),
			@TPriority(field="complement", priority=Priority.ALWAYS), 
			@TPriority(field="neighborhood", priority=Priority.ALWAYS), 
			@TPriority(field="code", priority=Priority.SOMETIMES)}))
	private SimpleObjectProperty<StreetType> streetType;
	
	@TLabel(text=TUsualKey.PUBLIC_PLACE)
	@TTextField(maxLength=120, required = true, 
		control=@TControl(tooltip=LocatKey.TEXT_MAPVIEW_PRESS_ENTER, parse = true), 
		node=@TNode(requestFocus=true, parse = true))
	private SimpleStringProperty publicPlace;
	
	@TLabel(text=TUsualKey.COMPLEMENT)
	@TTextField(maxLength=120)
	private SimpleStringProperty complement;

	@TLabel(text=TUsualKey.NEIGHBORHOOD)
	@TTextField(maxLength=120)
	private SimpleStringProperty neighborhood;
	
	@TLabel(text=TUsualKey.ADDRESS_CODE)
	@TTextField(maxLength=20, 
	control=@TControl(tooltip=LocatKey.TEXT_MAPVIEW_PRESS_ENTER, parse = true),
	textInputControl=@TTextInputControl(promptText=LocatKey.TEXT_ADDRESS_CODE, parse = true))
	private SimpleStringProperty code;
	
	@TLabel(text="Latitude")
	@TTextField(maxLength=15, 
			control=@TControl(tooltip=LocatKey.TEXT_MAPVIEW_PRESS_ENTER, parse = true))
	@THBox(	pane=@TPane(children={"latitude", "logintude"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="latitude", priority=Priority.ALWAYS), 
			@TPriority(field="logintude", priority=Priority.ALWAYS)}))
	private SimpleStringProperty latitude;
	
	@TLabel(text="Logintude")
	@TTextField(maxLength=15, 
			control=@TControl(tooltip=LocatKey.TEXT_MAPVIEW_PRESS_ENTER, parse = true))
	private SimpleStringProperty logintude;
	
	@TWebView(prefHeight=300,
			engine=@TWebEngine(load=TWebEngine.MODULE_FOLDER+"/"+TConstant.UUI+"/location.html"))
	private SimpleStringProperty webview;
	
	public AddressMV(Address entity) {
		super(entity);
		this.formatToString("%s %s %s", this.streetType, this.publicPlace, this.complement);
	}

	public SimpleObjectProperty<StreetType> getStreetType() {
		return streetType;
	}

	public void setStreetType(SimpleObjectProperty<StreetType> streetType) {
		this.streetType = streetType;
	}

	public SimpleStringProperty getPublicPlace() {
		return publicPlace;
	}

	public void setPublicPlace(SimpleStringProperty publicPlace) {
		this.publicPlace = publicPlace;
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

}

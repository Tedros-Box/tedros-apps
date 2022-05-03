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
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TEditModalPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.presenter.modal.decorator.TEditModalDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.location.domain.DomainApp;
import com.tedros.location.model.Address;
import com.tedros.location.model.AdminArea;
import com.tedros.location.model.City;
import com.tedros.location.model.Country;
import com.tedros.location.model.StreetType;
import com.tedros.location.module.country.model.CountryMV;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */

@TEjbService(serviceName = "IAddressControllerRemote", model=Address.class)
@TEditModalPresenter(
		presenter=@TPresenter(decorator = @TDecorator(type=TEditModalDecorator.class,
				viewTitle="#{view.address}", buildSaveButton=true, buildDeleteButton=true)))
@TSecurity(	id=DomainApp.ADDRESS_FORM_ID, 
appName = "#{app.location.name}", moduleName = "#{module.administrative}", viewName = "#{view.address}",
allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
				TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})

public class AddressMV extends TEntityModelView<Address> {


	private SimpleLongProperty id;
	
	private SimpleStringProperty display;
	
	@TReaderHtml
	@TLabel(text="#{label.street.type}")
	@TComboBoxField(firstItemTex="#{label.select}", required=true,
		optionsList=@TOptionsList(serviceName = "IStreetTypeControllerRemote", 
		optionModelViewClass=StreetTypeMV.class,
		entityClass=StreetType.class))
	@THBox(	pane=@TPane(children={"streetType", "plubicPlace"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="streetType", priority=Priority.SOMETIMES), 
			@TPriority(field="plubicPlace", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<StreetType> streetType;
	
	@TReaderHtml
	@TLabel(text="#{label.public.place}")
	@TTextField(maxLength=120, required = true, 
		node=@TNode(requestFocus=true, parse = true))
	private SimpleStringProperty plubicPlace;
	
	@TReaderHtml
	@TLabel(text="#{label.complement}")
	@TTextField(maxLength=120, required = true)
	@THBox(	pane=@TPane(children={"complement", "neighborhood", "code"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="complement", priority=Priority.ALWAYS), 
			@TPriority(field="neighborhood", priority=Priority.ALWAYS), 
			@TPriority(field="code", priority=Priority.SOMETIMES)}))
	private SimpleStringProperty complement;

	@TReaderHtml
	@TLabel(text="#{label.neighborhood}")
	@TTextField(maxLength=120, required = false)
	private SimpleStringProperty neighborhood;
	
	@TReaderHtml
	@TLabel(text="#{label.address.code}")
	@TTextField(maxLength=20, required = false)
	private SimpleStringProperty code;
	
	@TReaderHtml
	@TLabel(text="#{label.country}")
	@TComboBoxField(firstItemTex="#{label.select}", required=true,
		optionsList=@TOptionsList(serviceName = "ICountryControllerRemote", 
		optionModelViewClass=CountryMV.class,
		entityClass=Country.class))
	@THBox(	pane=@TPane(children={"country", "adminArea", "city"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="country", priority=Priority.ALWAYS), 
			@TPriority(field="adminArea", priority=Priority.ALWAYS), 
			@TPriority(field="city", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<Country> country;
	
	@TReaderHtml
	@TLabel(text="#{label.admin.area}")
	@TComboBoxField(firstItemTex="#{label.select}", required=false)
	private SimpleObjectProperty<AdminArea> adminArea;
	
	@TReaderHtml
	@TLabel(text="#{label.city}")
	@TComboBoxField(firstItemTex="#{label.select}", required=false)
	private SimpleObjectProperty<City> city;
	
	public AddressMV(Address entity) {
		super(entity);
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

}

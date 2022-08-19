/**
 * 
 */
package com.tedros.location.module.city.model;

import static com.tedros.core.annotation.security.TAuthorizationType.DELETE;
import static com.tedros.core.annotation.security.TAuthorizationType.EDIT;
import static com.tedros.core.annotation.security.TAuthorizationType.NEW;
import static com.tedros.core.annotation.security.TAuthorizationType.SAVE;
import static com.tedros.core.annotation.security.TAuthorizationType.VIEW_ACCESS;
import static com.tedros.fxapi.TUsualKey.ADMIN_AREA;
import static com.tedros.fxapi.TUsualKey.CAPITAL;
import static com.tedros.fxapi.TUsualKey.NAME;
import static com.tedros.fxapi.TUsualKey.POPULATION;

import com.tedros.core.annotation.security.TSecurity;
import com.tedros.ejb.controller.ICityController;
import com.tedros.fxapi.TUsualKey;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TNumberSpinnerField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.view.TOption;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.location.LocatKey;
import com.tedros.location.domain.DomainApp;
import com.tedros.location.model.City;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = LocatKey.FORM_KEEP_UPDATE, showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = ICityController.JNDI_NAME, model=City.class)
@TListViewPresenter(listViewMinWidth=350, listViewMaxWidth=350,
	paginator=@TPaginator(entityClass = City.class, serviceName=ICityController.JNDI_NAME,
			show=true, showSearchField=true, searchFieldName="name", 
			orderBy = {	@TOption(text = TUsualKey.COUNTRY_CODE, value = "countryIso2Code"), 
						@TOption(text = TUsualKey.NAME, value = "name")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=LocatKey.VIEW_CITY, buildImportButton=true),
	behavior=@TBehavior(importModelViewClass=CityImportMV.class, runNewActionAfterSave=true)))
@TSecurity(	id=DomainApp.CITY_FORM_ID, appName = LocatKey.APP_LOCATION_NAME,
moduleName = LocatKey.MODULE_ADMINISTRATIVE, viewName = LocatKey.VIEW_CITY,
allowedAccesses={VIEW_ACCESS, EDIT, SAVE, DELETE, NEW})
public class CityMV extends TEntityModelView<City> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty display;
	
	@TLabel(text=TUsualKey.COUNTRY_CODE+" (ISO2)")
	@TTextField(maxLength=2, required = true, node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"countryIso2Code", "name", "capital", "adminArea", "population"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="countryIso2Code", priority=Priority.NEVER), 
			@TPriority(field="population", priority=Priority.NEVER), 
			@TPriority(field="name", priority=Priority.ALWAYS), 
			@TPriority(field="adminArea", priority=Priority.ALWAYS), 
			@TPriority(field="capital", priority=Priority.ALWAYS)}))
	private SimpleStringProperty countryIso2Code;
			
	@TLabel(text=NAME)
	@TTextField(maxLength=60, required = true)
	private SimpleStringProperty name;
			
	@TLabel(text=CAPITAL)
	@TTextField(maxLength=120, required = true)
	private SimpleStringProperty capital;
			
	@TLabel(text = POPULATION)
	@TNumberSpinnerField(maxValue = Long.MAX_VALUE)
	private SimpleLongProperty population;
	
	@TLabel(text=ADMIN_AREA)
	@TTextField(maxLength=120, required = false)
	private SimpleStringProperty adminArea;
	
	@TReaderHtml
	@TLabel(text="Latitude")
	@TTextField(maxLength=20)
	@THBox(	pane=@TPane(children={"latitude", "longitude"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="latitude", priority=Priority.SOMETIMES), 
			@TPriority(field="longitude", priority=Priority.SOMETIMES)}))
	private SimpleStringProperty latitude;
			
	@TReaderHtml
	@TLabel(text="Longitude")
	@TTextField(maxLength=20)
	private SimpleStringProperty longitude;
			
	
	public CityMV(City e) {
		super(e);
		this.formatFieldsToDisplay("[%s] %s / %s", this.countryIso2Code, this.adminArea, this.name);
	}
	
	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return display;
	}

	public SimpleStringProperty getCountryIso2Code() {
		return countryIso2Code;
	}

	public void setCountryIso2Code(SimpleStringProperty countryIso2Code) {
		this.countryIso2Code = countryIso2Code;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getCapital() {
		return capital;
	}

	public void setCapital(SimpleStringProperty capital) {
		this.capital = capital;
	}

	public SimpleLongProperty getPopulation() {
		return population;
	}

	public void setPopulation(SimpleLongProperty population) {
		this.population = population;
	}

	public SimpleStringProperty getLatitude() {
		return latitude;
	}

	public void setLatitude(SimpleStringProperty latitude) {
		this.latitude = latitude;
	}

	public SimpleStringProperty getLongitude() {
		return longitude;
	}

	public void setLongitude(SimpleStringProperty longitude) {
		this.longitude = longitude;
	}

	public SimpleStringProperty getAdminArea() {
		return adminArea;
	}

	public void setAdminArea(SimpleStringProperty adminArea) {
		this.adminArea = adminArea;
	}

	public SimpleStringProperty getDisplay() {
		return display;
	}

	public void setDisplay(SimpleStringProperty display) {
		this.display = display;
	}

}

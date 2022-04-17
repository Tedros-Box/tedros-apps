/**
 * 
 */
package com.tedros.location.module.city.model;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
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
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.view.TOption;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.location.domain.DomainApp;
import com.tedros.location.model.AdminArea;
import com.tedros.location.model.City;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = "#{form.keep.update}", showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = "ICityControllerRemote", model=City.class)
@TListViewPresenter(listViewMinWidth=350, listViewMaxWidth=350,
	paginator=@TPaginator(entityClass = City.class, serviceName = "ICityControllerRemote",
			show=true, showSearchField=true, searchFieldName="name", 
			orderBy = {	@TOption(text = "#{label.country.code}", value = "countryIso2Code"), 
						@TOption(text = "#{label.name}", value = "name")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="#{view.city}", buildImportButton=true),
	behavior=@TBehavior(importModelViewClass=CityImportMV.class, runNewActionAfterSave=true)))
@TSecurity(	id=DomainApp.CITY_FORM_ID, 
	appName = "#{app.location.name}", moduleName = "#{module.administrative}", viewName = "#{view.city}",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class CityMV extends TEntityModelView<City> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty display;
	
	@TReaderHtml
	@TLabel(text="#{label.country.code} (ISO2)")
	@TTextField(maxLength=2, required = true, node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"countryIso2Code", "name", "capital", "adminArea", "population"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="countryIso2Code", priority=Priority.NEVER), 
			@TPriority(field="population", priority=Priority.NEVER), 
			@TPriority(field="name", priority=Priority.ALWAYS), 
			@TPriority(field="adminArea", priority=Priority.ALWAYS), 
			@TPriority(field="capital", priority=Priority.ALWAYS)}))
	private SimpleStringProperty countryIso2Code;
			
	@TReaderHtml
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=60, required = true)
	private SimpleStringProperty name;
			
	@TReaderHtml
	@TLabel(text="#{label.capital}")
	@TTextField(maxLength=120, required = true)
	private SimpleStringProperty capital;
			
	@TReaderHtml
	@TLabel(text = "#{label.population}")
	@TNumberSpinnerField(maxValue = Long.MAX_VALUE)
	private SimpleLongProperty population;
	
	@TReaderHtml
	@TLabel(text="#{label.admin.area}")
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
		this.buildDisplayText();
		this.setDisplayText(e.getCountryIso2Code(), e.getAdminArea(), e.getName());
	}
	
	@Override
	public void reload(City e) {
		super.reload(e);
		this.buildDisplayText();
		this.setDisplayText(e.getCountryIso2Code(), e.getAdminArea(), e.getName());
	}
	
	private void buildDisplayText() {
		ChangeListener<String> cchl = super.getListenerRepository().get("countryChl");
		if(cchl==null) {
			cchl = (a,o,n)->{
				this.setDisplayText(n, this.adminArea.getValue(), this.name.getValue());
			};
		}else
			super.removeListener("countryChl");
		super.getListenerRepository().add("countryChl", cchl);
		this.countryIso2Code.addListener(cchl);

		ChangeListener<String> achl = super.getListenerRepository().get("admnameChl");
		if(achl==null) {
			achl = (a,o,n)->{
				this.setDisplayText(this.countryIso2Code.getValue(), n, this.name.getValue());
			};
		}else
			super.removeListener("admnameChl");
		super.getListenerRepository().add("admnameChl", achl);
		this.adminArea.addListener(achl);
		
		ChangeListener<String> nchl = super.getListenerRepository().get("nameChl");
		if(nchl==null) {
			nchl = (a,o,n)->{
				this.setDisplayText(this.countryIso2Code.getValue(), this.adminArea.getValue(), n);
			};
		}else
			super.removeListener("nameChl");
		super.getListenerRepository().add("nameChl", nchl);
		this.name.addListener(nchl);
	}
	
	private void setDisplayText(String country, String adm, String name) {
		String s = (StringUtils.isNotBlank(country) ? "["+country+"] " : "") 
				+ (StringUtils.isNotBlank(adm) ? adm +" / " : "")
				+ (StringUtils.isNotBlank(name) ? name : "");
		if(this.display==null)
			this.display = new SimpleStringProperty();
		this.display.setValue(s);
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

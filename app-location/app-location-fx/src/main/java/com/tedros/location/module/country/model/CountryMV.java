/**
 * 
 */
package com.tedros.location.module.country.model;

import static com.tedros.core.annotation.security.TAuthorizationType.DELETE;
import static com.tedros.core.annotation.security.TAuthorizationType.EDIT;
import static com.tedros.core.annotation.security.TAuthorizationType.NEW;
import static com.tedros.core.annotation.security.TAuthorizationType.SAVE;
import static com.tedros.core.annotation.security.TAuthorizationType.VIEW_ACCESS;

import com.tedros.core.annotation.security.TSecurity;
import com.tedros.ejb.base.model.ITFileBaseModel;
import com.tedros.ejb.controller.ICountryController;
import com.tedros.fxapi.TUsualKey;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TNumberSpinnerField;
import com.tedros.fxapi.annotation.control.TSelectImageField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TDetailForm;
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
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.view.TOption;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.domain.TEnvironment;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.location.LocatKey;
import com.tedros.location.domain.DomainApp;
import com.tedros.location.model.Country;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = LocatKey.FORM_KEEP_UPDATE, showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = ICountryController.JNDI_NAME, model=Country.class)
@TListViewPresenter(listViewMinWidth=350, listViewMaxWidth=350,
	paginator=@TPaginator(entityClass = Country.class, serviceName = ICountryController.JNDI_NAME,
			show=true, showSearchField=true, searchFieldName="name", 
			orderBy = {	@TOption(text = TUsualKey.COUNTRY_CODE, value = "iso2Code"), 
						@TOption(text = TUsualKey.NAME, value = "name")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=LocatKey.VIEW_COUNTRY, buildImportButton=true),
	behavior=@TBehavior(importModelViewClass=CountryImportMV.class, runNewActionAfterSave=true)))
@TSecurity(	id=DomainApp.COUNTRY_FORM_ID, appName = LocatKey.APP_LOCATION_NAME,
moduleName = LocatKey.MODULE_ADMINISTRATIVE, viewName = LocatKey.VIEW_COUNTRY,
allowedAccesses={VIEW_ACCESS, EDIT, SAVE, DELETE, NEW})
public class CountryMV extends TEntityModelView<Country> {

	@TTabPane(tabs = { 
			@TTab(closable=false, scroll=false, content = @TContent(detailForm=
				@TDetailForm(fields={"iso2Code", "numericCode", "currencyCode","latitude"})), 
				text = TUsualKey.MAIN), 
			@TTab(closable=false, content = @TContent(detailForm=
				@TDetailForm(fields={"flag"})), 
				text = TUsualKey.FLAG)
	})
	private SimpleLongProperty id;
	
	@TLabel(text=TUsualKey.COUNTRY_CODE+" (ISO2)")
	@TTextField(maxLength=2, required = true, node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"iso2Code", "iso3Code", "name", "capital"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="iso2Code", priority=Priority.NEVER), 
			@TPriority(field="iso3Code", priority=Priority.NEVER), 
			@TPriority(field="name", priority=Priority.ALWAYS), 
			@TPriority(field="capital", priority=Priority.ALWAYS)}))
	private SimpleStringProperty iso2Code;
	
	@TLabel(text=TUsualKey.COUNTRY_CODE+" (ISO3)")
	@TTextField(maxLength=3, required = true)
	private SimpleStringProperty iso3Code;
			
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=60, required = true)
	private SimpleStringProperty name;
			
	@TLabel(text=TUsualKey.CAPITAL)
	@TTextField(maxLength=120, required = true)
	private SimpleStringProperty capital;
			
	@TLabel(text = TUsualKey.NUMERIC_CODE)
	@TNumberSpinnerField(maxValue = Long.MAX_VALUE)
	@THBox(	pane=@TPane(children={"numericCode", "iddCode", "populationName", "totalArea", "population"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="numericCode", priority=Priority.NEVER), 
			@TPriority(field="iddCode", priority=Priority.NEVER), 
			@TPriority(field="populationName", priority=Priority.ALWAYS), 
			@TPriority(field="totalArea", priority=Priority.NEVER), 
			@TPriority(field="population", priority=Priority.NEVER)}))
	private SimpleLongProperty numericCode;
			
	@TLabel(text=TUsualKey.POPULATION_NAME)
	@TTextField(maxLength=60)
	private SimpleStringProperty populationName;
			
	@TLabel(text = TUsualKey.TOTAL_AREA)
	@TNumberSpinnerField(maxValue = Long.MAX_VALUE)
	private SimpleLongProperty totalArea;
			
	@TLabel(text = TUsualKey.POPULATION)
	@TNumberSpinnerField(maxValue = Long.MAX_VALUE)
	private SimpleLongProperty population;
			
	@TLabel(text = TUsualKey.IDD_CODE)
	@TNumberSpinnerField(maxValue = Long.MAX_VALUE)
	private SimpleIntegerProperty iddCode;
			
	@TLabel(text=TUsualKey.CURRENCY_CODE)
	@TTextField(maxLength=3)
	@THBox(	pane=@TPane(children={"currencyCode", "currencyName", "langCode", "langName", "cctld"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="currencyCode", priority=Priority.NEVER), 
			@TPriority(field="currencyName", priority=Priority.ALWAYS), 
			@TPriority(field="langCode", priority=Priority.NEVER), 
			@TPriority(field="langName", priority=Priority.ALWAYS), 
			@TPriority(field="cctld", priority=Priority.NEVER)}))
	private SimpleStringProperty currencyCode;
			
	@TLabel(text=TUsualKey.CURRENCY_NAME)
	@TTextField(maxLength=60)
	private SimpleStringProperty currencyName;
	
	@TLabel(text=TUsualKey.LANG_CODE)
	@TTextField(maxLength=2)
	private SimpleStringProperty langCode;
	
	@TLabel(text=TUsualKey.LANG_NAME)
	@TTextField(maxLength=60)
	private SimpleStringProperty langName;
	
	@TLabel(text=TUsualKey.CCTLD)
	@TTextField(maxLength=2)
	private SimpleStringProperty cctld;
			
	@TLabel(text="Latitude")
	@TTextField(maxLength=20)
	@THBox(	pane=@TPane(children={"latitude", "longitude"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="latitude", priority=Priority.SOMETIMES), 
			@TPriority(field="longitude", priority=Priority.SOMETIMES)}))
	private SimpleStringProperty latitude;
			
	@TLabel(text="Longitude")
	@TTextField(maxLength=20)
	private SimpleStringProperty longitude;
			
	@TFieldBox(node=@TNode(id="img", parse = true))
	@TSelectImageField(source=TEnvironment.LOCAL, target=TEnvironment.REMOTE, 
	remoteOwner=DomainApp.MNEMONIC, maxFileSize=300000)
	private SimpleObjectProperty<ITFileBaseModel> flag;
	
	public CountryMV(Country entity) {
		super(entity);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getIso2Code() {
		return iso2Code;
	}

	public void setIso2Code(SimpleStringProperty iso2Code) {
		this.iso2Code = iso2Code;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getIso3Code() {
		return iso3Code;
	}

	public void setIso3Code(SimpleStringProperty iso3Code) {
		this.iso3Code = iso3Code;
	}

	public SimpleLongProperty getNumericCode() {
		return numericCode;
	}

	public void setNumericCode(SimpleLongProperty numericCode) {
		this.numericCode = numericCode;
	}

	public SimpleStringProperty getCapital() {
		return capital;
	}

	public void setCapital(SimpleStringProperty capital) {
		this.capital = capital;
	}

	public SimpleStringProperty getPopulationName() {
		return populationName;
	}

	public void setPopulationName(SimpleStringProperty populationName) {
		this.populationName = populationName;
	}

	public SimpleLongProperty getTotalArea() {
		return totalArea;
	}

	public void setTotalArea(SimpleLongProperty totalArea) {
		this.totalArea = totalArea;
	}

	public SimpleLongProperty getPopulation() {
		return population;
	}

	public void setPopulation(SimpleLongProperty population) {
		this.population = population;
	}

	public SimpleIntegerProperty getIddCode() {
		return iddCode;
	}

	public void setIddCode(SimpleIntegerProperty iddCode) {
		this.iddCode = iddCode;
	}

	public SimpleStringProperty getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(SimpleStringProperty currencyCode) {
		this.currencyCode = currencyCode;
	}

	public SimpleStringProperty getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(SimpleStringProperty currencyName) {
		this.currencyName = currencyName;
	}

	public SimpleStringProperty getLangCode() {
		return langCode;
	}

	public void setLangCode(SimpleStringProperty langCode) {
		this.langCode = langCode;
	}

	public SimpleStringProperty getLangName() {
		return langName;
	}

	public void setLangName(SimpleStringProperty langName) {
		this.langName = langName;
	}

	public SimpleStringProperty getCctld() {
		return cctld;
	}

	public void setCctld(SimpleStringProperty cctld) {
		this.cctld = cctld;
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

	public SimpleObjectProperty<ITFileBaseModel> getFlag() {
		return flag;
	}

	public void setFlag(SimpleObjectProperty<ITFileBaseModel> flag) {
		this.flag = flag;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return name;
	}

}

/**
 * 
 */
package com.tedros.location.module.country.model;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.ejb.base.model.ITFileBaseModel;
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
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.view.TOption;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.domain.TEnvironment;
import com.tedros.fxapi.presenter.model.TEntityModelView;
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
@TFormReaderHtml
@TForm(name = "#{form.keep.update}", showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = "ICountryControllerRemote", model=Country.class)
@TListViewPresenter(listViewMinWidth=350, listViewMaxWidth=350,
	paginator=@TPaginator(entityClass = Country.class, serviceName = "ICountryControllerRemote",
			show=true, showSearchField=true, searchFieldName="name", 
			orderBy = {	@TOption(text = "#{label.country.code}", value = "iso2Code"), 
						@TOption(text = "#{label.name}", value = "name")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="#{view.country}", buildImportButton=true),
	behavior=@TBehavior(importModelViewClass=CountryImportMV.class, runNewActionAfterSave=true)))
@TSecurity(	id=DomainApp.COUNTRY_FORM_ID, 
	appName = "#{app.location.name}", moduleName = "#{module.administrative}", viewName = "#{view.country}",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class CountryMV extends TEntityModelView<Country> {

	@TTabPane(tabs = { 
			@TTab(closable=false, scroll=false, content = @TContent(detailForm=
				@TDetailForm(fields={"iso2Code", "numericCode", "currencyCode","latitude"})), 
				text = "#{label.main}"), 
			@TTab(closable=false, content = @TContent(detailForm=
				@TDetailForm(fields={"flag"})), 
				text = "#{label.flag}")
	})
	private SimpleLongProperty id;
	
	@TReaderHtml
	@TLabel(text="#{label.country.code} (ISO2)")
	@TTextField(maxLength=2, required = true, node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"iso2Code", "iso3Code", "name", "capital"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="iso2Code", priority=Priority.NEVER), 
			@TPriority(field="iso3Code", priority=Priority.NEVER), 
			@TPriority(field="name", priority=Priority.ALWAYS), 
			@TPriority(field="capital", priority=Priority.ALWAYS)}))
	private SimpleStringProperty iso2Code;
			
	@TReaderHtml
	@TLabel(text="#{label.country.code} (ISO3)")
	@TTextField(maxLength=3, required = true)
	private SimpleStringProperty iso3Code;
			
	@TReaderHtml
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=60, required = true)
	private SimpleStringProperty name;
			
	@TReaderHtml
	@TLabel(text="#{label.capital}")
	@TTextField(maxLength=120, required = true)
	private SimpleStringProperty capital;
			
	@TReaderHtml
	@TLabel(text = "#{label.numeric.code}")
	@TNumberSpinnerField(maxValue = Long.MAX_VALUE)
	@THBox(	pane=@TPane(children={"numericCode", "iddCode", "populationName", "totalArea", "population"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="numericCode", priority=Priority.NEVER), 
			@TPriority(field="iddCode", priority=Priority.NEVER), 
			@TPriority(field="populationName", priority=Priority.ALWAYS), 
			@TPriority(field="totalArea", priority=Priority.NEVER), 
			@TPriority(field="population", priority=Priority.NEVER)}))
	private SimpleLongProperty numericCode;
			
	@TReaderHtml
	@TLabel(text="#{label.population.name}")
	@TTextField(maxLength=60)
	private SimpleStringProperty populationName;
			
	@TReaderHtml
	@TLabel(text = "#{label.total.area}")
	@TNumberSpinnerField(maxValue = Long.MAX_VALUE)
	private SimpleLongProperty totalArea;
			
	@TReaderHtml
	@TLabel(text = "#{label.population}")
	@TNumberSpinnerField(maxValue = Long.MAX_VALUE)
	private SimpleLongProperty population;
			
	@TReaderHtml
	@TLabel(text = "#{label.idd.code}")
	@TNumberSpinnerField(maxValue = Long.MAX_VALUE)
	private SimpleIntegerProperty iddCode;
			
	@TReaderHtml
	@TLabel(text="#{label.currency.code}")
	@TTextField(maxLength=3)
	@THBox(	pane=@TPane(children={"currencyCode", "currencyName", "langCode", "langName", "cctld"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="currencyCode", priority=Priority.NEVER), 
			@TPriority(field="currencyName", priority=Priority.ALWAYS), 
			@TPriority(field="langCode", priority=Priority.NEVER), 
			@TPriority(field="langName", priority=Priority.ALWAYS), 
			@TPriority(field="cctld", priority=Priority.NEVER)}))
	private SimpleStringProperty currencyCode;
			
	@TReaderHtml
	@TLabel(text="#{label.currency.name}")
	@TTextField(maxLength=60)
	private SimpleStringProperty currencyName;
	
	@TReaderHtml
	@TLabel(text="#{label.lang.code}")
	@TTextField(maxLength=2)
	private SimpleStringProperty langCode;
	
	@TReaderHtml
	@TLabel(text="#{label.lang.name}")
	@TTextField(maxLength=60)
	private SimpleStringProperty langName;
	
	@TReaderHtml
	@TLabel(text="#{label.cctld}")
	@TTextField(maxLength=2)
	private SimpleStringProperty cctld;
			
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
			
	@TReaderHtml
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

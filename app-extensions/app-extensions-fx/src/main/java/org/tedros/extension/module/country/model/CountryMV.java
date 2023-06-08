/**
 * 
 */
package org.tedros.extension.module.country.model;

import static org.tedros.core.annotation.security.TAuthorizationType.DELETE;
import static org.tedros.core.annotation.security.TAuthorizationType.EDIT;
import static org.tedros.core.annotation.security.TAuthorizationType.NEW;
import static org.tedros.core.annotation.security.TAuthorizationType.SAVE;
import static org.tedros.core.annotation.security.TAuthorizationType.VIEW_ACCESS;

import org.tedros.core.annotation.security.TSecurity;
import org.tedros.extension.LocatKey;
import org.tedros.extension.domain.DomainApp;
import org.tedros.extension.ejb.controller.ICountryController;
import org.tedros.extension.model.Country;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TContent;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TNumberSpinnerField;
import org.tedros.fx.annotation.control.TSelectImageField;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TDetailForm;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.page.TPage;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TOrder;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.domain.TEnvironment;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.server.model.ITFileBaseModel;
import org.tedros.server.query.TCompareOp;

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
@TListViewPresenter(
	page=@TPage(serviceName = ICountryController.JNDI_NAME,
	query = @TQuery(entity=Country.class, condition= {
			@TCondition(field = "iso2Code", operator=TCompareOp.EQUAL, label=TUsualKey.COUNTRY_CODE),
			@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME)},
		orderBy= {@TOrder(label = TUsualKey.NAME, field = "name"),
				@TOrder(label = TUsualKey.COUNTRY_CODE, field = "iso2Code")}
			),showSearch=true, showOrderBy=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=LocatKey.VIEW_COUNTRY, buildImportButton=true),
	behavior=@TBehavior(importModelViewClass=CountryImportMV.class, runNewActionAfterSave=true)))
@TSecurity(	id=DomainApp.COUNTRY_FORM_ID, appName = LocatKey.APP_LOCATION_NAME,
moduleName = LocatKey.MODULE_ADMINISTRATIVE, viewName = LocatKey.VIEW_COUNTRY,
allowedAccesses={VIEW_ACCESS, EDIT, SAVE, DELETE, NEW})
public class CountryMV extends TEntityModelView<Country> {

	@TTabPane(tabs = { 
		@TTab(text = TUsualKey.MAIN, 
			content = @TContent(detailForm=@TDetailForm(
			fields={"iso2Code", "capital", "populationName", "currencyCode","cctld"}))), 
		@TTab(text = TUsualKey.FLAG,
			content = @TContent(detailForm=@TDetailForm(fields={"flag"})))})
	private SimpleLongProperty code;
	
	@TLabel(text=TUsualKey.COUNTRY_CODE+" (ISO2)")
	@TTextField(maxLength=2, required = true, node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"iso2Code", "iso3Code", "name"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="iso2Code", priority=Priority.NEVER), 
			@TPriority(field="iso3Code", priority=Priority.NEVER), 
			@TPriority(field="name", priority=Priority.ALWAYS)}))
	private SimpleStringProperty iso2Code;
	
	@TLabel(text=TUsualKey.COUNTRY_CODE+" (ISO3)")
	@TTextField(maxLength=3, required = true)
	private SimpleStringProperty iso3Code;
			
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=60, required = true)
	private SimpleStringProperty name;
			
	@TLabel(text=TUsualKey.CAPITAL)
	@TTextField(maxLength=120, required = true)
	@THBox(	pane=@TPane(children={"capital","numericCode", "iddCode"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="numericCode", priority=Priority.NEVER), 
			@TPriority(field="iddCode", priority=Priority.NEVER), 
			@TPriority(field="capital", priority=Priority.ALWAYS)}))
	private SimpleStringProperty capital;
			
	@TLabel(text = TUsualKey.NUMERIC_CODE)
	@TNumberSpinnerField(maxValue = Long.MAX_VALUE)
	private SimpleLongProperty numericCode;
			
	@TLabel(text = TUsualKey.IDD_CODE)
	@TNumberSpinnerField(maxValue = Long.MAX_VALUE)
	private SimpleIntegerProperty iddCode;
			
	@TLabel(text=TUsualKey.POPULATION_NAME)
	@TTextField(maxLength=60)
	@THBox(	pane=@TPane(children={"populationName", "totalArea", "population"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="populationName", priority=Priority.ALWAYS), 
			@TPriority(field="totalArea", priority=Priority.NEVER), 
			@TPriority(field="population", priority=Priority.NEVER)}))
	private SimpleStringProperty populationName;
			
	@TLabel(text = TUsualKey.TOTAL_AREA)
	@TNumberSpinnerField(maxValue = Long.MAX_VALUE)
	private SimpleLongProperty totalArea;
			
	@TLabel(text = TUsualKey.POPULATION)
	@TNumberSpinnerField(maxValue = Long.MAX_VALUE)
	private SimpleLongProperty population;
			
	@TLabel(text=TUsualKey.CURRENCY_CODE)
	@TTextField(maxLength=3)
	@THBox(	pane=@TPane(children={"currencyCode", "currencyName", "langCode", "langName"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="currencyCode", priority=Priority.NEVER), 
			@TPriority(field="currencyName", priority=Priority.ALWAYS), 
			@TPriority(field="langCode", priority=Priority.NEVER), 
			@TPriority(field="langName", priority=Priority.ALWAYS)}))
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
	@THBox(	pane=@TPane(children={"cctld", "latitude", "longitude"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="latitude", priority=Priority.SOMETIMES), 
			@TPriority(field="longitude", priority=Priority.SOMETIMES), 
			@TPriority(field="cctld", priority=Priority.NEVER)}))
	private SimpleStringProperty cctld;
			
	@TLabel(text="Latitude")
	@TTextField(maxLength=20)
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
	public SimpleStringProperty toStringProperty() {
		return name;
	}


	public SimpleLongProperty getCode() {
		return code;
	}


	public void setCode(SimpleLongProperty code) {
		this.code = code;
	}

}

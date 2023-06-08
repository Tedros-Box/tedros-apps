/**
 * 
 */
package org.tedros.extension.module.city.model;

import static org.tedros.core.annotation.security.TAuthorizationType.DELETE;
import static org.tedros.core.annotation.security.TAuthorizationType.EDIT;
import static org.tedros.core.annotation.security.TAuthorizationType.NEW;
import static org.tedros.core.annotation.security.TAuthorizationType.SAVE;
import static org.tedros.core.annotation.security.TAuthorizationType.VIEW_ACCESS;
import static org.tedros.fx.TUsualKey.ADMIN_AREA;
import static org.tedros.fx.TUsualKey.CAPITAL;
import static org.tedros.fx.TUsualKey.NAME;
import static org.tedros.fx.TUsualKey.POPULATION;

import org.tedros.core.annotation.security.TSecurity;
import org.tedros.extension.LocatKey;
import org.tedros.extension.domain.DomainApp;
import org.tedros.extension.ejb.controller.ICityController;
import org.tedros.extension.model.City;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TNumberSpinnerField;
import org.tedros.fx.annotation.control.TTextField;
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
import org.tedros.fx.annotation.reader.TReaderHtml;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.server.query.TCompareOp;

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
	page=@TPage(serviceName = ICityController.JNDI_NAME,
	query = @TQuery(entity=City.class, condition= {
			@TCondition(field = "countryIso2Code", operator=TCompareOp.EQUAL, label=TUsualKey.COUNTRY_CODE),
			@TCondition(field = "adminArea", operator=TCompareOp.LIKE, label=TUsualKey.ADMIN_AREA),
			@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME)},
		orderBy= {@TOrder(label = TUsualKey.COUNTRY_CODE, field = "countryIso2Code"),
				@TOrder(label = TUsualKey.ADMIN_AREA, field = "adminArea"),
				@TOrder(label = TUsualKey.NAME, field = "name")}
			),showSearch=true, showOrderBy=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=LocatKey.VIEW_CITY, buildImportButton=true),
	behavior=@TBehavior(importModelViewClass=CityImportMV.class, runNewActionAfterSave=true)))
@TSecurity(	id=DomainApp.CITY_FORM_ID, appName = LocatKey.APP_LOCATION_NAME,
moduleName = LocatKey.MODULE_ADMINISTRATIVE, viewName = LocatKey.VIEW_CITY,
allowedAccesses={VIEW_ACCESS, EDIT, SAVE, DELETE, NEW})
public class CityMV extends TEntityModelView<City> {
	
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
		this.formatToString("[%s] %s / %s", this.countryIso2Code, this.adminArea, this.name);
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
}

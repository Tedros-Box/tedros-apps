package org.tedros.location.module.report.model;

import static org.tedros.core.annotation.security.TAuthorizationType.EXPORT;
import static org.tedros.core.annotation.security.TAuthorizationType.SEARCH;
import static org.tedros.core.annotation.security.TAuthorizationType.VIEW_ACCESS;

import org.tedros.core.annotation.security.TSecurity;
import org.tedros.ejb.controller.IPlaceTypeController;
import org.tedros.ejb.controller.IStreetTypeController;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.control.TOptionsList;
import org.tedros.fx.annotation.control.TRadioButton;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.control.TVerticalRadioGroup;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.TAccordion;
import org.tedros.fx.annotation.layout.TFieldSet;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.layout.TTitledPane;
import org.tedros.fx.annotation.layout.TVBox;
import org.tedros.fx.annotation.layout.TVGrow;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TReportProcess;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.layout.TRegion;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.domain.TLayoutType;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.fx.presenter.report.behavior.TDataSetReportBehavior;
import org.tedros.fx.presenter.report.decorator.TDataSetReportDecorator;
import org.tedros.location.LocatKey;
import org.tedros.location.annotation.TAdminAreaComboBox;
import org.tedros.location.annotation.TCityComboBox;
import org.tedros.location.annotation.TCountryComboBox;
import org.tedros.location.domain.DomainApp;
import org.tedros.location.model.AdminArea;
import org.tedros.location.model.City;
import org.tedros.location.model.Country;
import org.tedros.location.model.PlaceType;
import org.tedros.location.model.StreetType;
import org.tedros.location.module.address.model.StreetTypeMV;
import org.tedros.location.module.place.model.PlaceTypeMV;
import org.tedros.location.module.report.action.SearchAction;
import org.tedros.location.module.report.process.PlaceReportProcess;
import org.tedros.location.module.report.table.PlaceRowFactoryBuilder;
import org.tedros.location.report.model.PlaceItemModel;
import org.tedros.location.report.model.PlaceReportModel;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;


@TForm(name = LocatKey.FORM_REPO_PLACE, showBreadcrumBar=true, editCssId="")
@TReportProcess(type=PlaceReportProcess.class, model = PlaceReportModel.class)
@TPresenter(type = TDynaPresenter.class,
			behavior = @TBehavior(type = TDataSetReportBehavior.class, 
			action=SearchAction.class), 
			decorator = @TDecorator(type = TDataSetReportDecorator.class, 
									viewTitle=LocatKey.VIEW_REPO_PLACE))
@TSecurity(	id=DomainApp.PLACE_REPORT_FORM_ID, appName = LocatKey.APP_LOCATION_NAME,
moduleName = LocatKey.MODULE_ADMINISTRATIVE, viewName = LocatKey.VIEW_ADDRESS,
allowedAccesses={VIEW_ACCESS, EXPORT, SEARCH})
public class PlaceReportMV extends TModelView<PlaceReportModel>{
	
	
	@TAccordion(expandedPane="filtro", node=@TNode(id="repdoaacc",parse = true),
			panes={
					@TTitledPane(text=TUsualKey.FILTERS, node=@TNode(id="filtro",parse = true), 
							expanded=true, layoutType=TLayoutType.HBOX,
							fields={"title", "orderBy"}),
					@TTitledPane(text=TUsualKey.RESULT, node=@TNode(id="resultado",parse = true),
						fields={"result"})})	
	private SimpleStringProperty displayText;
	
	@TLabel(text=TUsualKey.TITLE)
	@TTextField(maxLength=60,node=@TNode(requestFocus=true, parse = true))
	@TVBox(	pane=@TPane(children={"title", "type", "country", "streetType"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="title", priority=Priority.ALWAYS), 
			@TPriority(field="type", priority=Priority.NEVER)}))
	private SimpleStringProperty title;
	
	@TLabel(text=TUsualKey.TYPE)
	@TComboBoxField(firstItemTex=TUsualKey.SELECT,
		optionsList=@TOptionsList(serviceName = IPlaceTypeController.JNDI_NAME, 
		optionModelViewClass=PlaceTypeMV.class,
		entityClass=PlaceType.class))
	private SimpleObjectProperty<PlaceType> type;
	
	@TLabel(text=TUsualKey.COUNTRY)
	@TCountryComboBox
	@THBox(	pane=@TPane(children={"country", "adminArea", "city"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="country", priority=Priority.ALWAYS), 
			@TPriority(field="adminArea", priority=Priority.ALWAYS), 
			@TPriority(field="city", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<Country> country;
	
	@TLabel(text=TUsualKey.ADMIN_AREA)
	@TAdminAreaComboBox(countryField="country")
	private SimpleObjectProperty<AdminArea> adminArea;
	
	@TLabel(text=TUsualKey.CITY)
	@TCityComboBox(countryField="country", adminAreaField="adminArea")
	private SimpleObjectProperty<City> city;
	
	@TLabel(text=TUsualKey.STREET_TYPE)
	@TComboBoxField(firstItemTex=TUsualKey.SELECT,
		optionsList=@TOptionsList(serviceName = IStreetTypeController.JNDI_NAME, 
		optionModelViewClass=StreetTypeMV.class,
		entityClass=StreetType.class))
	@THBox(	pane=@TPane(children={"streetType", "publicPlace", "code"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="streetType", priority=Priority.SOMETIMES), 
			@TPriority(field="publicPlace", priority=Priority.ALWAYS), 
			@TPriority(field="code", priority=Priority.SOMETIMES)}))
	private SimpleObjectProperty<StreetType> streetType;
	
	@TLabel(text=TUsualKey.PUBLIC_PLACE)
	@TTextField(maxLength=120)
	private SimpleStringProperty publicPlace;
	
	@TLabel(text=TUsualKey.ADDRESS_CODE)
	@TTextField(maxLength=20)
	private SimpleStringProperty code;
	
	@TLabel(text=TFxKey.SORT_BY)
	@TFieldSet(fields = { "orderBy", "orderType" }, 
		region=@TRegion(maxWidth=600, parse = true),
		legend = TUsualKey.RESULT_ORDER)
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButton(text=TUsualKey.TITLE, userData="e.title"), 
					@TRadioButton(text=TUsualKey.TYPE, userData="t.name"), 
					@TRadioButton(text=TUsualKey.COUNTRY, userData="c.name"), 
					@TRadioButton(text=TUsualKey.ADMIN_AREA, userData="aa.name"), 
					@TRadioButton(text=TUsualKey.CITY, userData="ct.name"), 
					@TRadioButton(text=TUsualKey.CODE, userData="a.code")
	})
	private SimpleStringProperty orderBy;
	
	@TLabel(text=TFxKey.SORT_TYPE)
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButton(text=TFxKey.SORT_BY_ASC, userData="asc"), 
					@TRadioButton(text=TFxKey.SORT_BY_DESC, userData="desc")
	})
	private SimpleStringProperty orderType;
	
	@TTableView(editable=true, rowFactory=PlaceRowFactoryBuilder.class,
			control=@TControl(tooltip=TFxKey.TABLE_MENU_TOOLTIP, parse = true),
			columns = { @TTableColumn(cellValue="title", text = TUsualKey.TITLE, prefWidth=20, resizable=true), 
					@TTableColumn(cellValue="type", text = TUsualKey.TYPE, resizable=true), 
						@TTableColumn(cellValue="country", text = TUsualKey.COUNTRY, resizable=true), 
						@TTableColumn(cellValue="address", text = TUsualKey.ADDRESS, resizable=true)
			})
	@TModelViewType(modelClass=PlaceItemModel.class, modelViewClass=PlaceItemMV.class)
	private ITObservableList<PlaceItemMV> result;
	
	public PlaceReportMV(PlaceReportModel entidade) {
		super(entidade);
	}
	

	/**
	 * @return the displayText
	 */
	public SimpleStringProperty getDisplayText() {
		return displayText;
	}

	/**
	 * @param displayText the displayText to set
	 */
	public void setDisplayText(SimpleStringProperty displayText) {
		this.displayText = displayText;
	}


	public SimpleStringProperty getTitle() {
		return title;
	}


	public void setTitle(SimpleStringProperty title) {
		this.title = title;
	}


	public SimpleObjectProperty<PlaceType> getType() {
		return type;
	}


	public void setType(SimpleObjectProperty<PlaceType> type) {
		this.type = type;
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


	public SimpleStringProperty getCode() {
		return code;
	}


	public void setCode(SimpleStringProperty code) {
		this.code = code;
	}


	public SimpleStringProperty getOrderBy() {
		return orderBy;
	}


	public void setOrderBy(SimpleStringProperty orderBy) {
		this.orderBy = orderBy;
	}


	public SimpleStringProperty getOrderType() {
		return orderType;
	}


	public void setOrderType(SimpleStringProperty orderType) {
		this.orderType = orderType;
	}


	public ITObservableList<PlaceItemMV> getResult() {
		return result;
	}


	public void setResult(ITObservableList<PlaceItemMV> result) {
		this.result = result;
	}

}

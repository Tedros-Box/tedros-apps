package com.tedros.location.module.report.model;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.TFxKey;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TRadioButton;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.control.TVerticalRadioGroup;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.TAccordion;
import com.tedros.fxapi.annotation.layout.TFieldSet;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.layout.TTitledPane;
import com.tedros.fxapi.annotation.layout.TVBox;
import com.tedros.fxapi.annotation.layout.TVGrow;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TReportProcess;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.annotation.scene.layout.TRegion;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.domain.TLayoutType;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.presenter.report.behavior.TDataSetReportBehavior;
import com.tedros.fxapi.presenter.report.decorator.TDataSetReportDecorator;
import com.tedros.location.annotation.TAdminAreaComboBox;
import com.tedros.location.annotation.TCityComboBox;
import com.tedros.location.annotation.TCountryComboBox;
import com.tedros.location.domain.DomainApp;
import com.tedros.location.model.AdminArea;
import com.tedros.location.model.City;
import com.tedros.location.model.Country;
import com.tedros.location.model.PlaceType;
import com.tedros.location.model.StreetType;
import com.tedros.location.module.address.model.StreetTypeMV;
import com.tedros.location.module.place.model.PlaceTypeMV;
import com.tedros.location.module.report.action.SearchAction;
import com.tedros.location.module.report.process.PlaceReportProcess;
import com.tedros.location.module.report.table.PlaceRowFactoryBuilder;
import com.tedros.location.report.model.PlaceItemModel;
import com.tedros.location.report.model.PlaceReportModel;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;


@TForm(name = "#{form.repo.place}", showBreadcrumBar=true, editCssId="")
@TReportProcess(type=PlaceReportProcess.class, model = PlaceReportModel.class)
@TPresenter(type = TDynaPresenter.class,
			behavior = @TBehavior(type = TDataSetReportBehavior.class, 
			action=SearchAction.class), 
			decorator = @TDecorator(type = TDataSetReportDecorator.class, 
									viewTitle="#{view.repo.place}"))
@TSecurity(	id=DomainApp.PLACE_REPORT_FORM_ID, 
			appName = "#{app.location.name}", moduleName = "#{module.administrative}", viewName = "#{view.repo.place}",
			allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EXPORT, TAuthorizationType.SEARCH})
public class PlaceReportMV extends TModelView<PlaceReportModel>{
	
	private SimpleLongProperty id;
	
	@TAccordion(expandedPane="filtro", node=@TNode(id="repdoaacc",parse = true),
			panes={
					@TTitledPane(text="#{label.filters}", node=@TNode(id="filtro",parse = true), 
							expanded=true, layoutType=TLayoutType.HBOX,
							fields={"title", "orderBy"}),
					@TTitledPane(text="#{label.result}", node=@TNode(id="resultado",parse = true),
						fields={"result"})})	
	private SimpleStringProperty displayText;
	
	@TLabel(text="#{label.title}")
	@TTextField(maxLength=60,node=@TNode(requestFocus=true, parse = true))
	@TVBox(	pane=@TPane(children={"title", "type", "country", "streetType"}), spacing=10, fillWidth=true,
	vgrow=@TVGrow(priority={@TPriority(field="title", priority=Priority.ALWAYS), 
			@TPriority(field="type", priority=Priority.NEVER)}))
	private SimpleStringProperty title;
	
	@TLabel(text="#{label.type}")
	@TComboBoxField(firstItemTex="#{label.select}",
		optionsList=@TOptionsList(serviceName = "IPlaceTypeControllerRemote", 
		optionModelViewClass=PlaceTypeMV.class,
		entityClass=PlaceType.class))
	private SimpleObjectProperty<PlaceType> type;
	
	@TLabel(text="#{label.country}")
	@TCountryComboBox(firstItemTex="#{label.select}")
	@THBox(	pane=@TPane(children={"country", "adminArea", "city"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="country", priority=Priority.ALWAYS), 
			@TPriority(field="adminArea", priority=Priority.ALWAYS), 
			@TPriority(field="city", priority=Priority.ALWAYS)}))
	private SimpleObjectProperty<Country> country;
	
	@TLabel(text="#{label.admin.area}")
	@TAdminAreaComboBox(firstItemTex="#{label.select}", countryField="country")
	private SimpleObjectProperty<AdminArea> adminArea;
	
	@TLabel(text="#{label.city}")
	@TCityComboBox(firstItemTex="#{label.select}", countryField="country", adminAreaField="adminArea")
	private SimpleObjectProperty<City> city;
	
	@TLabel(text="#{label.street.type}")
	@TComboBoxField(firstItemTex="#{label.select}",
		optionsList=@TOptionsList(serviceName = "IStreetTypeControllerRemote", 
		optionModelViewClass=StreetTypeMV.class,
		entityClass=StreetType.class))
	@THBox(	pane=@TPane(children={"streetType", "publicPlace", "code"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="streetType", priority=Priority.SOMETIMES), 
			@TPriority(field="publicPlace", priority=Priority.ALWAYS), 
			@TPriority(field="code", priority=Priority.SOMETIMES)}))
	private SimpleObjectProperty<StreetType> streetType;
	
	@TLabel(text="#{label.public.place}")
	@TTextField(maxLength=120, 
		control=@TControl(tooltip="#{text.mapview.press.enter}", parse = true), 
		node=@TNode(requestFocus=true, parse = true))
	private SimpleStringProperty publicPlace;
	
	@TLabel(text="#{label.address.code}")
	@TTextField(maxLength=20, 
	control=@TControl(tooltip="#{text.mapview.press.enter}", parse = true),
	textInputControl=@TTextInputControl(promptText="#{text.address.code}", parse = true))
	private SimpleStringProperty code;
	
	
	
	@TLabel(text=TFxKey.SORT_BY)
	@TFieldSet(fields = { "orderBy", "orderType" }, 
		region=@TRegion(maxWidth=600, parse = true),
		legend = "#{label.result.order}")
	@TVerticalRadioGroup(alignment=Pos.TOP_LEFT, spacing=4,
	radioButtons = {@TRadioButton(text="#{label.title}", userData="e.title"), 
					@TRadioButton(text="#{label.type}", userData="t.name"), 
					@TRadioButton(text="#{label.country}", userData="c.name"), 
					@TRadioButton(text="#{label.admin.area}", userData="aa.name"), 
					@TRadioButton(text="#{label.city}", userData="ct.name"), 
					@TRadioButton(text="#{label.code}", userData="a.code")
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
			columns = { @TTableColumn(cellValue="title", text = "#{label.title}", prefWidth=20, resizable=true), 
					@TTableColumn(cellValue="type", text = "#{label.type}", resizable=true), 
						@TTableColumn(cellValue="country", text = "#{label.country}", resizable=true), 
						@TTableColumn(cellValue="address", text = "#{label.address}", resizable=true)
			})
	@TModelViewType(modelClass=PlaceItemModel.class, modelViewClass=PlaceItemMV.class)
	private ITObservableList<PlaceItemMV> result;
	
	public PlaceReportMV(PlaceReportModel entidade) {
		super(entidade);
	}
	
	
	@Override
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	@Override
	public SimpleLongProperty getId() {
		return id;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return this.displayText;
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

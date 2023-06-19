/**
 * 
 */
package org.tedros.samples.module.control.model;

import org.tedros.extension.ejb.controller.ICountryController;
import org.tedros.extension.model.Country;
import org.tedros.extension.module.country.model.CountryMV;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TAutoCompleteTextField;
import org.tedros.fx.annotation.control.TCallbackFactory;
import org.tedros.fx.annotation.control.TCellFactory;
import org.tedros.fx.annotation.control.TAutoCompleteTextField.TEntry;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TListViewField;
import org.tedros.fx.annotation.control.TPickListField;
import org.tedros.fx.annotation.control.TProcess;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TOrder;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.control.tablecell.TMediumDateTimeCallback;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;
import org.tedros.samples.SmplsKey;
import org.tedros.samples.module.control.builder.CountriesListBuilder;
import org.tedros.samples.module.control.builder.CountriesObservableListBuilder;
import org.tedros.server.query.TCompareOp;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.SelectionMode;

/**
 * @author Davis Gordon
 *
 */
@TPresenter(modelClass=FieldModel.class,
	decorator=@TDecorator(type=TViewDecorator.class, viewTitle="Advanced components"),
	behavior=@TBehavior(type=TViewBehavior.class))
public class AdvancedControlMV extends TModelView<FieldModel> {
	
	@TText(textStyle = TTextStyle.LARGE, text="Samples of advanced components")
	@TFieldBox(node=@TNode(parse = true, id=TFieldBox.TITLE))
	private SimpleStringProperty header;
	
	@TTabPane(tabs = { 
			@TTab(fields = { "autoCompleteTextField", "listview0", "picklistview0" }, text = "Input controls"),
			@TTab(fields = { "list0" }, text = "Table View") 	
		})
	private SimpleStringProperty tab;
	
	@TLabel(text="Auto Complete (Static entries): "+TUsualKey.STREET_TYPE)
	@TAutoCompleteTextField(
			entries = @TEntry(
				values= {SmplsKey.OPT_ALLEY,SmplsKey.OPT_AVENUE,
						SmplsKey.OPT_BOULEVARD,SmplsKey.OPT_LANE,
						SmplsKey.OPT_ROAD,SmplsKey.OPT_STREET}))
	@TFlowPane(hgap=20,
	pane=@TPane(children={"autoCompleteTextField","autoCompleteTextField0","autoCompleteEntity",
			"combobox0","combobox1"}))
	private SimpleStringProperty autoCompleteTextField;
	
	@TLabel(text="Auto Complete (Factored entries): "+SmplsKey.EUROPEAN_COUNTRY)
	@TAutoCompleteTextField(
			entries = @TEntry(factory=CountriesListBuilder.class))
	private SimpleStringProperty autoCompleteTextField0;
	
	@TLabel(text="Auto Complete (Database entries): "+TUsualKey.COUNTRY)
	@TAutoCompleteEntity(service = ICountryController.JNDI_NAME,
		query = @TQuery(entity = Country.class, 
			condition=@TCondition(field = "name", operator=TCompareOp.LIKE),
			orderBy=@TOrder(field = "name")))
	private SimpleObjectProperty<Country> autoCompleteEntity;
	
	@TLabel(text="Combo Box (Static items): "+SmplsKey.EUROPEAN_COUNTRY)
	@TComboBoxField(items=CountriesObservableListBuilder.class)
	private SimpleObjectProperty<Country> combobox0;

	@TLabel(text="Combo Box (Database items): "+TUsualKey.COUNTRY)
	@TComboBoxField(
		process=@TProcess(service = ICountryController.JNDI_NAME, 
		query = @TQuery(entity = Country.class, orderBy=@TOrder(field="name"))))
	private SimpleObjectProperty<Country> combobox1;

	@TLabel(text="List View (Multiple selection from static items):\n "+SmplsKey.EUROPEAN_COUNTRIES)
	@TListViewField(items=CountriesObservableListBuilder.class, 
	control=@TControl(parse=true, maxHeight=100, prefHeight=100))
	@TFlowPane(hgap=20,
	pane=@TPane(children={"listview0","listview1"}))
	@TGenericType(model = Country.class)
	private ITObservableList<Country> listview0;

	@TLabel(text="List View (Single Selection from database):\n"+SmplsKey.COUNTRIES)
	@TListViewField(control=@TControl(parse=true, maxHeight=100, prefHeight=100),
		process=@TProcess(service = ICountryController.JNDI_NAME, 
		query = @TQuery(entity = Country.class, orderBy=@TOrder(field="name"))))
	private SimpleObjectProperty<Country> listview1;

	@TLabel(text="Pick List (Source from static list)")
	@TPickListField(height=100, 
		sourceLabel=SmplsKey.EUROPEAN_COUNTRIES,
		targetLabel=TUsualKey.SELECTEDS,
		selectionMode=SelectionMode.MULTIPLE,
		items=CountriesObservableListBuilder.class)
	@TFlowPane(hgap=20,
	pane=@TPane(children={"picklistview0","picklistview1"}))
	@TGenericType(model = Country.class)
	private ITObservableList<Country> picklistview0;

	@TLabel(text="Pick List (Source from database) ")
	@TPickListField(height=100, 
		sourceLabel=SmplsKey.COUNTRIES,
		targetLabel=TUsualKey.SELECTEDS,
		selectionMode=SelectionMode.MULTIPLE,
		process=@TProcess(service = ICountryController.JNDI_NAME, 
		query = @TQuery(entity = Country.class, orderBy=@TOrder(field="name"))))
	@TGenericType(model = Country.class)
	private ITObservableList<CountryMV> picklistview1;
	
	@TTableView(columns = { 
			@TTableColumn(text = "Field string0", cellValue="string0"),
			@TTableColumn(text = "Field integer0", cellValue="integer0"),
			@TTableColumn(text = "Field long0", cellValue="long0"),
			@TTableColumn(text = "Field double0", cellValue="double0"),
			@TTableColumn(text = "Field date0", cellValue="date0", 
					cellFactory=@TCellFactory(parse = true, 
					callBack=@TCallbackFactory(parse=true, value=TMediumDateTimeCallback.class))),
			@TTableColumn(text = "Field detail0", cellValue="detail0")
	})
	@TGenericType(model = MasterModel.class, modelView=MasterMV.class)
	private ITObservableList<MasterMV> list0;

	public AdvancedControlMV(FieldModel model) {
		super(model);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

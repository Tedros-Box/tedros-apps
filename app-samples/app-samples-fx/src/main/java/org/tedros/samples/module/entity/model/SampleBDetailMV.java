/**
 * 
 */
package org.tedros.samples.module.entity.model;

import org.tedros.extension.component.annotation.TAdminAreaComboBox;
import org.tedros.extension.component.annotation.TCityComboBox;
import org.tedros.extension.component.annotation.TCountryComboBox;
import org.tedros.extension.model.AdminArea;
import org.tedros.extension.model.City;
import org.tedros.extension.model.Country;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.presenter.TDetailTableViewPresenter;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.sample.entity.SampleB;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
@TDetailTableViewPresenter(
	tableView = @TTableView(
		control=@TControl(maxHeight=200, parse = true),
		columns = 
		{
			@TTableColumn(text = TUsualKey.NAME, cellValue="name"),  
			@TTableColumn(text = TUsualKey.COUNTRY, cellValue="country"), 
			@TTableColumn(text = TUsualKey.ADMIN_AREA, cellValue="adminArea"), 
			@TTableColumn(text = TUsualKey.CITY, cellValue="city")
		}))
public class SampleBDetailMV extends TEntityModelView<SampleB> {

	@TLabel(text=TUsualKey.NAME)
	@TTextField
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.COUNTRY)
	@TCountryComboBox
	@TFlowPane(hgap=20,
	pane=@TPane(children={"country","adminArea","city"}))
	private SimpleObjectProperty<Country> country;
	
	@TLabel(text=TUsualKey.ADMIN_AREA)
	@TAdminAreaComboBox(countryField="country")
	private SimpleObjectProperty<AdminArea> adminArea;
	
	@TLabel(text=TUsualKey.CITY)
	@TCityComboBox(countryField="country", 
	adminAreaField="adminArea")
	private SimpleObjectProperty<City> city;
	
	
	public SampleBDetailMV(SampleB entity) {
		super(entity);
		super.formatToString("%s", name);
	}

}

/**
 * 
 */
package org.tedros.samples.module.entity.model;

import java.util.Date;

import org.tedros.extension.ejb.controller.ICountryController;
import org.tedros.extension.model.AdminArea;
import org.tedros.extension.model.Country;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TIntegratedLinkField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TProcess;
import org.tedros.fx.annotation.control.TShowField;
import org.tedros.fx.annotation.control.TShowField.TField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.form.TSetting;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TOrder;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.domain.TTimeStyle;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.sample.ejb.controller.ISampleBController;
import org.tedros.sample.entity.SampleB;
import org.tedros.samples.module.entity.setting.SampleSetting;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;

/**
 * @author Davis Gordon
 *
 */
@TSetting(SampleSetting.class)
@TForm(showBreadcrumBar=false, scroll=false)
@TEjbService(serviceName = ISampleBController.JNDI_NAME, model=SampleB.class)
@TListViewPresenter(
	presenter=@TPresenter(
		decorator = @TDecorator(viewTitle="Setting sample", 
		buildSaveButton=false, buildDeleteButton=false)))
public class SettingSampleMV extends TEntityModelView<SampleB> {

	@TLabel(text=TUsualKey.NAME)
	@TTextField
	private SimpleStringProperty name;
	
	@TText(textStyle = TTextStyle.LARGE, 
	text="The Administrative Area combo box is updated by the selected country. \nThis behavior was configured with @TSetting")
	@TFieldBox(alignment=Pos.CENTER_LEFT,
	node=@TNode(id=TFieldBox.TITLE, parse = true))
	private SimpleStringProperty text1;
	
	@TLabel(text=TUsualKey.COUNTRY)
	@TComboBoxField(
		process=@TProcess(service = ICountryController.JNDI_NAME,
			query = @TQuery(entity = Country.class, 
			orderBy=@TOrder(field = "name"))))
	@TFlowPane(hgap=20,
	pane=@TPane(children={"country","adminArea"}))
	private SimpleObjectProperty<Country> country;
	
	@TLabel(text=TUsualKey.ADMIN_AREA)
	@TComboBoxField
	private SimpleObjectProperty<AdminArea> adminArea;
	
	@TLabel(text=TFxKey.INTEGRATED_BY)
	@TShowField()
	@TFlowPane(pane=@TPane(
		children={"integratedViewName", "integratedDate", "integratedModulePath"}))
	private SimpleStringProperty integratedViewName;
	
	@TLabel(text=TFxKey.INTEGRATED_DATE)
	@TShowField(fields= {@TField(timeStyle=TTimeStyle.SHORT)})
	private SimpleObjectProperty<Date> integratedDate;
	
	@TLabel(text=TFxKey.INTEGRATED_LINK)
	@TIntegratedLinkField
	private SimpleStringProperty integratedModulePath;
	
	
	public SettingSampleMV(SampleB entity) {
		super(entity);
		super.formatToString("%s", name);
	}


	/**
	 * @return the country
	 */
	public SimpleObjectProperty<Country> getCountry() {
		return country;
	}


	/**
	 * @return the adminArea
	 */
	public SimpleObjectProperty<AdminArea> getAdminArea() {
		return adminArea;
	}

}

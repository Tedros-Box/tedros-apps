/**
 * 
 */
package org.tedros.samples.module.entity.model;

import java.util.Date;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.extension.component.annotation.TAdminAreaComboBox;
import org.tedros.extension.component.annotation.TCityComboBox;
import org.tedros.extension.component.annotation.TCountryComboBox;
import org.tedros.extension.model.AdminArea;
import org.tedros.extension.model.City;
import org.tedros.extension.model.Country;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TIntegratedLinkField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TShowField;
import org.tedros.fx.annotation.control.TShowField.TField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.domain.TTimeStyle;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.sample.domain.DomainApp;
import org.tedros.sample.ejb.controller.ISampleBController;
import org.tedros.sample.entity.SampleB;
import org.tedros.samples.SmplsKey;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;

/**
 * @author Davis Gordon
 *
 */

@TForm(header="This entity can be built externally", showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = ISampleBController.JNDI_NAME, model=SampleB.class)
@TListViewPresenter(
	presenter=@TPresenter(
		decorator = @TDecorator(viewTitle="Integrated entity sample")))
@TSecurity(id=DomainApp.SAMPLE_B_FORM_ID, appName = SmplsKey.APP_SAMPLES,
	moduleName = "Samples", viewName = "Integrated entity sample",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class IntegratedMV extends TEntityModelView<SampleB> {

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
	
	@TText(textStyle = TTextStyle.LARGE, text="Here we can see who created this entity and open it")
	@TFieldBox(alignment=Pos.CENTER_LEFT,
	node=@TNode(id=TFieldBox.TITLE, parse = true))
	private SimpleStringProperty text1;
	
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
	
	
	public IntegratedMV(SampleB entity) {
		super(entity);
		super.formatToString("%s", name);
	}

}

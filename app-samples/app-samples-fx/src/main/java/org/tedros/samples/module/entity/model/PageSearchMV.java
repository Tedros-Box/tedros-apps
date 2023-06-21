/**
 * 
 */
package org.tedros.samples.module.entity.model;

import org.tedros.extension.ejb.controller.ICountryController;
import org.tedros.extension.model.Country;
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
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TOrder;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.server.query.TCompareOp;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(header = "Header form", showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = ICountryController.JNDI_NAME, model=Country.class)
@TListViewPresenter(
	page=@TPage(serviceName = ICountryController.JNDI_NAME,
		query = @TQuery(entity=Country.class, condition= {
				@TCondition(field = "iso2Code", operator=TCompareOp.EQUAL, label=TUsualKey.COUNTRY_CODE),
				@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME)},
			orderBy= {
				@TOrder(label = TUsualKey.NAME, field = "name"),
				@TOrder(label = TUsualKey.COUNTRY_CODE, field = "iso2Code")}
			),showSearch=true, showOrderBy=true),
	presenter=@TPresenter(
		decorator = @TDecorator(viewTitle="Page and Search entities", 
		buildSaveButton=false, buildDeleteButton=false,buildNewButton=false)))
public class PageSearchMV extends TEntityModelView<Country> {

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
			
	public PageSearchMV(Country entity) {
		super(entity);
		super.formatToString("[%s] %s", iso2Code, name);
	}


}

/**
 * 
 */
package org.tedros.samples.module.forms.model;

import org.tedros.extension.ejb.controller.ICountryController;
import org.tedros.extension.model.Country;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TAutoCompleteTextField;
import org.tedros.fx.annotation.control.TAutoCompleteTextField.TEntry;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.layout.TFlowPane;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TOrder;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.model.TModelView;
import org.tedros.fx.presenter.model.behavior.TViewBehavior;
import org.tedros.fx.presenter.model.decorator.TViewDecorator;
import org.tedros.samples.SmplsKey;
import org.tedros.samples.module.forms.builder.CountriesListBuilder;
import org.tedros.server.query.TCompareOp;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
@TPresenter(decorator=@TDecorator(type=TViewDecorator.class, 
viewTitle="Advanced components"),
behavior=@TBehavior(type=TViewBehavior.class))
public class AdvancedControlMV extends TModelView<FieldModel> {
	
	@TText(textStyle = TTextStyle.LARGE, text="Samples of advanced components")
	@TFieldBox(node=@TNode(parse = true, id=TFieldBox.TITLE))
	private SimpleStringProperty header;
	
	@TLabel(text="Auto Complete Text Field: "+TUsualKey.STREET_TYPE)
	@TAutoCompleteTextField(
			entries = @TEntry(
				values= {SmplsKey.OPT_ALLEY,SmplsKey.OPT_AVENUE,
						SmplsKey.OPT_BOULEVARD,SmplsKey.OPT_LANE,
						SmplsKey.OPT_ROAD,SmplsKey.OPT_STREET}))
	@TFlowPane(hgap=20,
			pane=@TPane(children={"autoCompleteTextField","autoCompleteTextField0","autoCompleteEntity"}))
	private SimpleStringProperty autoCompleteTextField;
	
	@TLabel(text="Auto Complete Text Field: "+SmplsKey.EUROPEAN_COUNTRY)
	@TAutoCompleteTextField(
			entries = @TEntry(factory=CountriesListBuilder.class))
	private SimpleStringProperty autoCompleteTextField0;
	
	@TLabel(text="Auto Complete Entity: "+TUsualKey.COUNTRY)
	@TAutoCompleteEntity(service = ICountryController.JNDI_NAME,
		query = @TQuery(entity = Country.class, 
			condition=@TCondition(field = "name", operator=TCompareOp.LIKE),
			orderBy=@TOrder(field = "name")))
	private SimpleObjectProperty<Country> autoCompleteEntity;
	
	
	

	public AdvancedControlMV(FieldModel model) {
		super(model);
	}

}

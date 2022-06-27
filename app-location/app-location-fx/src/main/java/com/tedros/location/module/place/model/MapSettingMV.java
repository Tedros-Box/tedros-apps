/**
 * 
 */
package com.tedros.location.module.place.model;

import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TRadioButton;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.control.TText.TTextStyle;
import com.tedros.fxapi.presenter.entity.behavior.TSaveViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TSaveViewDecorator;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.location.module.place.action.MapSettingSaveAction;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TPresenter(decorator = @TDecorator(viewTitle="#{view.map.setting}", type=TSaveViewDecorator.class),
behavior=@TBehavior(type=TSaveViewBehavior.class, action=MapSettingSaveAction.class))
public class MapSettingMV extends TModelView<MapSettingModel> {

	@TLabel(text="#{label.map.type}")
	@THorizontalRadioGroup(radioButtons = { 
			@TRadioButton(text = "MapQuest", userData = MapSettingModel.TYPE_MAPQUEST)/*,
			@TRadioButtonField(text = "Google Maps", userData = MapSettingModel.TYPE_GOOGLE)*/})
	private SimpleStringProperty mapType;
	
	@TLabel(text="#{label.mapquest.key}")
	@TTextField(maxLength=120)
	private SimpleStringProperty mapquestKey;
	
	
	@TText(text="#{text.map.setting}", 
	textAlignment=TextAlignment.LEFT,/* wrappingWidth=750,*/
	textStyle = TTextStyle.MEDIUM)
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-fieldbox-hsplit-last", parse = true))
	private SimpleStringProperty text;
	
	public MapSettingMV() {
		super(new MapSettingModel());
	}
	
	public MapSettingMV(MapSettingModel model) {
		super(model);
	}

	@Override
	public void setId(SimpleLongProperty id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SimpleLongProperty getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	public SimpleStringProperty getMapType() {
		return mapType;
	}

	public void setMapType(SimpleStringProperty mapType) {
		this.mapType = mapType;
	}

	public SimpleStringProperty getMapquestKey() {
		return mapquestKey;
	}

	public void setMapquestKey(SimpleStringProperty mapquestKey) {
		this.mapquestKey = mapquestKey;
	}

	public SimpleStringProperty getText() {
		return text;
	}

	public void setText(SimpleStringProperty text) {
		this.text = text;
	}

}

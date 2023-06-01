/**
 * 
 */
package org.tedros.extension.module.place.model;

import org.tedros.extension.LocatKey;
import org.tedros.extension.module.place.action.MapSettingSaveAction;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.THorizontalRadioGroup;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TRadioButton;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.presenter.entity.behavior.TSaveViewBehavior;
import org.tedros.fx.presenter.entity.decorator.TSaveViewDecorator;
import org.tedros.fx.presenter.model.TModelView;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TPresenter(decorator = @TDecorator(viewTitle=LocatKey.VIEW_MAP_SETTING, type=TSaveViewDecorator.class),
behavior=@TBehavior(type=TSaveViewBehavior.class, action=MapSettingSaveAction.class))
public class MapSettingMV extends TModelView<MapSettingModel> {

	@TLabel(text=TUsualKey.MAP_TYPE)
	@THorizontalRadioGroup(radioButtons = { 
			@TRadioButton(text = "MapQuest", userData = MapSettingModel.TYPE_MAPQUEST)/*,
			@TRadioButtonField(text = "Google Maps", userData = MapSettingModel.TYPE_GOOGLE)*/})
	private SimpleStringProperty mapType;
	
	@TLabel(text=TUsualKey.MAPQUEST_KEY)
	@TTextField(maxLength=120)
	private SimpleStringProperty mapquestKey;
	
	
	@TText(text=LocatKey.TEXT_MAP_SETTING, 
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

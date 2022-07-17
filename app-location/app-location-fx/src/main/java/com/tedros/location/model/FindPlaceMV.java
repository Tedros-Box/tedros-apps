/**
 * 
 */
package com.tedros.location.model;

import com.tedros.fxapi.TUsualKey;
import com.tedros.fxapi.annotation.control.TCallbackFactory;
import com.tedros.fxapi.annotation.control.TCellValueFactory;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TLabelDefaultSetting;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.presenter.TSelectionModalPresenter;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.presenter.modal.behavior.TSelectionModalBehavior;
import com.tedros.fxapi.presenter.modal.decorator.TSelectionModalDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.location.LocatKey;
import com.tedros.location.module.place.model.PlaceTypeMV;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = LocatKey.VIEW_PLACE)
@TLabelDefaultSetting(font=@TFont(size=12))
@TSelectionModalPresenter(
	paginator=@TPaginator(entityClass = Place.class, modelViewClass=PlaceItemMV.class, 
		serviceName = "IPlaceControllerRemote"),
	presenter=@TPresenter(behavior = @TBehavior(type = TSelectionModalBehavior.class), 
		decorator = @TDecorator(type=TSelectionModalDecorator.class, viewTitle=LocatKey.VIEW_PLACE)),
	tableView=@TTableView(editable=true, 
		columns = {@TTableColumn(cellValue="title", text = TUsualKey.TITLE, resizable=true), 
			@TTableColumn(cellValue="type", text = TUsualKey.TYPE, resizable=true,
				cellValueFactory=@TCellValueFactory(parse=true, 
				value=@TCallbackFactory(parse=true, value=PlaceTypeCellCallBack.class))), 
			@TTableColumn(cellValue="address", text =TUsualKey.ADDRESS, resizable=true,
				cellValueFactory=@TCellValueFactory(parse=true, 
				value=@TCallbackFactory(parse=true, value=AddressCellCallBack.class)))
		}), 
	allowsMultipleSelections = false)
public class FindPlaceMV extends TEntityModelView<Place> {

	private SimpleLongProperty id;
	
	@TLabel(text=TUsualKey.TITLE)
	@TTextField(maxLength=60, node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"title", "type"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="title", priority=Priority.ALWAYS), 
			@TPriority(field="type", priority=Priority.SOMETIMES)}))
	private SimpleStringProperty title;
	
	@TLabel(text=TUsualKey.TYPE)
	@TComboBoxField(firstItemTex=TUsualKey.SELECT,
		optionsList=@TOptionsList(serviceName = "IPlaceTypeControllerRemote", 
		optionModelViewClass=PlaceTypeMV.class,
		entityClass=PlaceType.class))
	private SimpleObjectProperty<PlaceType> type;
	
	public FindPlaceMV(Place entity) {
		super(entity);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return title;
	}

	public SimpleObjectProperty<PlaceType> getType() {
		return type;
	}

	public void setType(SimpleObjectProperty<PlaceType> type) {
		this.type = type;
	}

	public SimpleStringProperty getTitle() {
		return title;
	}

	public void setTitle(SimpleStringProperty title) {
		this.title = title;
	}


}

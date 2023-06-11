/**
 * 
 */
package org.tedros.extension.model;

import org.tedros.extension.LocatKey;
import org.tedros.extension.ejb.controller.IExtensionDomainController;
import org.tedros.extension.ejb.controller.IPlaceController;
import org.tedros.extension.module.place.model.PlaceTypeMV;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TCallbackFactory;
import org.tedros.fx.annotation.control.TCellValueFactory;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TLabelDefaultSetting;
import org.tedros.fx.annotation.control.TOptionsList;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.page.TPage;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.presenter.TSelectionModalPresenter;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.text.TFont;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.presenter.modal.behavior.TSelectionModalBehavior;
import org.tedros.fx.presenter.modal.decorator.TSelectionModalDecorator;

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
	page=@TPage(query = @TQuery(entity=Place.class), 
		modelView=PlaceItemMV.class, 
		serviceName = IPlaceController.JNDI_NAME),
	presenter=@TPresenter(
		behavior = @TBehavior(type = TSelectionModalBehavior.class), 
		decorator = @TDecorator(type=TSelectionModalDecorator.class, viewTitle=LocatKey.VIEW_PLACE)),
	tableView=@TTableView(editable=true, 
		columns = {
			@TTableColumn(cellValue="title", text = TUsualKey.TITLE, resizable=true), 
			@TTableColumn(cellValue="type", text = TUsualKey.TYPE, resizable=true,
				cellValueFactory=@TCellValueFactory(parse=true, 
				value=@TCallbackFactory(parse=true, value=PlaceTypeCellCallBack.class))), 
			@TTableColumn(cellValue="address", text =TUsualKey.ADDRESS, resizable=true,
				cellValueFactory=@TCellValueFactory(parse=true, 
				value=@TCallbackFactory(parse=true, value=AddressCellCallBack.class)))
		}), 
	allowsMultipleSelections = false)
public class FindPlaceMV extends TEntityModelView<Place> {

	@TLabel(text=TUsualKey.TITLE)
	@TTextField(maxLength=60, node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"title", "type"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="title", priority=Priority.ALWAYS), 
			@TPriority(field="type", priority=Priority.SOMETIMES)}))
	private SimpleStringProperty title;
	
	@TLabel(text=TUsualKey.TYPE)
	@TComboBoxField(
		optionsList=@TOptionsList(serviceName = IExtensionDomainController.JNDI_NAME, 
		optionModelViewClass=PlaceTypeMV.class,
		entityClass=PlaceType.class))
	private SimpleObjectProperty<PlaceType> type;
	
	public FindPlaceMV(Place entity) {
		super(entity);
	}
	
	@Override
	public SimpleStringProperty toStringProperty() {
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

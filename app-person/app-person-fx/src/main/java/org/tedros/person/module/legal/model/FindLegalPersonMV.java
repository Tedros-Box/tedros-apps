/**
 * 
 */
package com.tedros.person.module.legal.model;

import com.tedros.fxapi.TUsualKey;
import com.tedros.fxapi.annotation.control.TCallbackFactory;
import com.tedros.fxapi.annotation.control.TCellValueFactory;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TLabelDefaultSetting;
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
import com.tedros.person.PersonKeys;
import com.tedros.person.ejb.controller.ILegalPersonController;
import com.tedros.person.model.LegalPerson;
import com.tedros.person.module.legal.table.EndActivitiesCellCallBack;
import com.tedros.person.module.legal.table.LegalPersonItemMV;
import com.tedros.person.module.legal.table.StartActivitiesCellCallBack;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */

@TForm(name = PersonKeys.TITLE_SELECT_LEGAL_PERSON)
@TLabelDefaultSetting(font=@TFont(size=12))
@TSelectionModalPresenter(
	paginator=@TPaginator(entityClass = LegalPerson.class, modelViewClass=LegalPersonItemMV.class, 
		serviceName = ILegalPersonController.JNDI_NAME),
	presenter=@TPresenter(behavior = @TBehavior(type = TSelectionModalBehavior.class), 
		decorator = @TDecorator(type=TSelectionModalDecorator.class, 
		viewTitle=PersonKeys.TITLE_SELECT_LEGAL_PERSON)),
	tableView=@TTableView(editable=true, 
		columns = { @TTableColumn(cellValue="name", text = TUsualKey.CORPORATE_NAME, prefWidth=20, resizable=true), 
			@TTableColumn(cellValue="otherName", text = TUsualKey.TRADE_NAME, prefWidth=20, resizable=true), 
			@TTableColumn(cellValue="startActivities", text = TUsualKey.START_ACTIVITIES, prefWidth=40, resizable=true,
				cellValueFactory=@TCellValueFactory(parse=true, 
				value=@TCallbackFactory(parse=true, value=StartActivitiesCellCallBack.class))),
			@TTableColumn(cellValue="endActivities", text = TUsualKey.END_ACTIVITIES, prefWidth=40, resizable=true,
				cellValueFactory=@TCellValueFactory(parse=true, 
				value=@TCallbackFactory(parse=true, value=EndActivitiesCellCallBack.class))),
			}), 
	allowsMultipleSelections = false)
public class FindLegalPersonMV extends TEntityModelView<LegalPerson> {

	private SimpleLongProperty id;
	
	@TLabel(text=TUsualKey.CORPORATE_NAME)
	@TTextField(maxLength=120, required = true,
		node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"name", "otherName"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS), 
			@TPriority(field="otherName", priority=Priority.ALWAYS)}))
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.TRADE_NAME)
	@TTextField(maxLength=60)
	private SimpleStringProperty otherName;
	
	public FindLegalPersonMV(LegalPerson entity) {
		super(entity);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getDisplayProperty() {
		return name;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getOtherName() {
		return otherName;
	}

	public void setOtherName(SimpleStringProperty otherName) {
		this.otherName = otherName;
	}

}

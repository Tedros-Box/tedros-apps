/**
 * 
 */
package org.tedros.person.model;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TCallbackFactory;
import org.tedros.fx.annotation.control.TCellFactory;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TLabelDefaultSetting;
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
import org.tedros.fx.control.tablecell.TShortDateCallback;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.fx.presenter.modal.behavior.TSelectionModalBehavior;
import org.tedros.fx.presenter.modal.decorator.TSelectionModalDecorator;
import org.tedros.person.PersonKeys;
import org.tedros.person.ejb.controller.ILegalPersonController;
import org.tedros.person.module.company.table.LegalPersonItemMV;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */

@TForm(header = PersonKeys.TITLE_SELECT_LEGAL_PERSON)
@TLabelDefaultSetting(font=@TFont(size=12))
@TSelectionModalPresenter(
	page=@TPage(query=@TQuery(entity=LegalPerson.class), 
		modelView=LegalPersonItemMV.class, 
		serviceName = ILegalPersonController.JNDI_NAME),
	presenter=@TPresenter(behavior = @TBehavior(type = TSelectionModalBehavior.class), 
		decorator = @TDecorator(type=TSelectionModalDecorator.class, 
		viewTitle=PersonKeys.TITLE_SELECT_LEGAL_PERSON)),
	tableView=@TTableView(editable=true, 
		columns = { 
			@TTableColumn(cellValue="name", text = TUsualKey.CORPORATE_NAME, prefWidth=20, resizable=true), 
			@TTableColumn(cellValue="otherName", text = TUsualKey.TRADE_NAME, prefWidth=20, resizable=true), 
			@TTableColumn(cellValue="startActivities", text = TUsualKey.START_ACTIVITIES, prefWidth=40, resizable=true,
				cellFactory=@TCellFactory(parse = true, 
					callBack=@TCallbackFactory(parse=true, value=TShortDateCallback.class))),
			@TTableColumn(cellValue="endActivities", text = TUsualKey.END_ACTIVITIES, prefWidth=40, resizable=true,
				cellFactory=@TCellFactory(parse = true, 
					callBack=@TCallbackFactory(parse=true, value=TShortDateCallback.class))),
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

	@Override
	public SimpleStringProperty toStringProperty() {
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

/**
 * 
 */
package org.tedros.person.model;

import java.util.Date;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TCallbackFactory;
import org.tedros.fx.annotation.control.TCellFactory;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TShowField;
import org.tedros.fx.annotation.control.TShowField.TField;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TDetailTableViewPresenter;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.control.tablecell.TMediumDateTimeCallback;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.util.TDateUtil;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TDetailTableViewPresenter(tableView = @TTableView(
control=@TControl(maxHeight=250,parse = true),
columns = 
	{ @TTableColumn(text = TUsualKey.DATE_INSERT, cellValue="insertDate", 
			cellFactory=@TCellFactory(parse = true, 
			callBack=@TCallbackFactory(parse=true, value=TMediumDateTimeCallback.class))), 
		@TTableColumn(text = TUsualKey.NAME, cellValue="name"), 
		@TTableColumn(text = TUsualKey.DESCRIPTION, cellValue="description"), 
	}))
public class PersonEventMV extends TEntityModelView<PersonEvent> {

	@TLabel(text="#{label.name}")
	@TTextField(maxLength=120, required = true, node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"insertDate","name"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="name", priority=Priority.ALWAYS), 
						@TPriority(field="insertDate", priority=Priority.SOMETIMES)}))
	private SimpleStringProperty name;

	@TLabel(text="#{label.date.insert}")
	@TShowField(fields= {@TField(pattern=TDateUtil.DDMMYYYY_HHMM)})
	private SimpleObjectProperty<Date> insertDate;

	@TLabel(text="#{label.description}")
	@TTextAreaField(maxLength=1024, wrapText=true, prefRowCount=3)
	private SimpleStringProperty description;
	
	/**
	 * @param entity
	 */
	public PersonEventMV(PersonEvent entity) {
		super(entity);
		// TODO Auto-generated constructor stub
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getDescription() {
		return description;
	}

	public void setDescription(SimpleStringProperty description) {
		this.description = description;
	}

	public SimpleObjectProperty<Date> getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(SimpleObjectProperty<Date> insertDate) {
		this.insertDate = insertDate;
	}
}

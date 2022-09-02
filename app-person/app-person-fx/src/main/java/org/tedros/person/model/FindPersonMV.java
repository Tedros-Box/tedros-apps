/**
 * 
 */
package org.tedros.person.model;

import org.tedros.person.ejb.controller.IPersonController;
import org.tedros.person.model.Employee;
import org.tedros.person.model.LegalPerson;
import org.tedros.person.model.NaturalPerson;
import org.tedros.person.model.Person;
import org.tedros.person.table.PersonCellCallBack;
import org.tedros.person.table.PersonItemMV;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TCallbackFactory;
import org.tedros.fx.annotation.control.TCellValueFactory;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TLabelDefaultSetting;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.presenter.TSelectionModalPresenter;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.text.TFont;
import org.tedros.fx.annotation.view.TPaginator;
import org.tedros.fx.presenter.modal.behavior.TSelectionModalBehavior;
import org.tedros.fx.presenter.modal.decorator.TSelectionModalDecorator;
import org.tedros.fx.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */

@TForm(name = "")
@TLabelDefaultSetting(font=@TFont(size=12))
@TSelectionModalPresenter(
	paginator=@TPaginator(entityClass = Person.class, modelViewClass=PersonItemMV.class, 
		serviceName = IPersonController.JNDI_NAME),
	presenter=@TPresenter(behavior = @TBehavior(type = TSelectionModalBehavior.class), 
		decorator = @TDecorator(type=TSelectionModalDecorator.class, 
		viewTitle=TUsualKey.SEARCH)),
	tableView=@TTableView(editable=true, 
		columns = { @TTableColumn(cellValue="name", text = TUsualKey.DETAILS,  
				cellValueFactory=@TCellValueFactory(parse=true, 
				value=@TCallbackFactory(parse=true, value=PersonCellCallBack.class)))
			}), 
	allowsMultipleSelections = false)
public class FindPersonMV extends TEntityModelView<Person> {

	private SimpleLongProperty id;
	
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=120, 
		node=@TNode(requestFocus=true, parse = true))
	private SimpleStringProperty name;
	
	public FindPersonMV(Person entity) {
		super(entity);
	}
	
	public FindPersonMV(LegalPerson entity) {
		super(entity);
	}
	
	public FindPersonMV(NaturalPerson entity) {
		super(entity);
	}
	
	public FindPersonMV(Employee entity) {
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

}

/**
 * 
 */
package org.tedros.person.module.category.model;

import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TAutoCompleteEntity.TEntry;
import org.tedros.fx.annotation.control.TButtonField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TModelViewType;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.control.TLabeled;
import org.tedros.fx.annotation.view.TOption;
import org.tedros.fx.annotation.view.TPaginator;
import org.tedros.fx.builder.TEditModelRowFactoryCallBackBuilder;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.person.PersonKeys;
import org.tedros.person.ejb.controller.ICustomerController;
import org.tedros.person.ejb.controller.IPersonCategoryController;
import org.tedros.person.ejb.controller.IPersonController;
import org.tedros.person.model.Customer;
import org.tedros.person.model.Person;
import org.tedros.person.model.PersonCategory;
import org.tedros.person.table.PersonTV;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */

@TForm(name = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = IPersonCategoryController.JNDI_NAME, model=PersonCategory.class)
@TListViewPresenter(
		paginator=@TPaginator(entityClass = PersonCategory.class, serviceName = IPersonCategoryController.JNDI_NAME,
		show=true, showSearchField=true, searchFieldName="name", 
		orderBy = {	@TOption(text = TUsualKey.NAME , value = "name")}),
		presenter=@TPresenter(decorator = @TDecorator(viewTitle="Category",
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))
public class CategoryMV extends TEntityModelView<PersonCategory> {

	@TLabel(text=TUsualKey.CODE)
	@TTextField(maxLength=20,
	node=@TNode(requestFocus=true, parse = true) )
	@THBox(	pane=@TPane(children={"code", "name"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="code", priority=Priority.NEVER), 
			@TPriority(field="name", priority=Priority.ALWAYS)}))
	private SimpleStringProperty code;
	
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=60, required = true)
	private SimpleStringProperty name;
	

	@TLabel(text=TUsualKey.SELECT)
	@TAutoCompleteEntity(modelViewType=PersonTV.class,
		entries = @TEntry(entityType = Person.class, field = "name", 
		service = IPersonController.JNDI_NAME))
	@THBox(	pane=@TPane(children={"item", "addBtn"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="item", priority=Priority.NEVER), 
			@TPriority(field="addBtn", priority=Priority.NEVER)}))
	private SimpleObjectProperty<PersonTV> item;

	@TButtonField(labeled = 
			@TLabeled(parse = true, text=TFxKey.BUTTON_ADD))
	private SimpleStringProperty addBtn;
	
	@TTableView(editable=true, rowFactory=TEditModelRowFactoryCallBackBuilder.class,
		control=@TControl(tooltip=TFxKey.TABLE_MENU_TOOLTIP, parse = true),
		columns = { 
				@TTableColumn(cellValue="toStringProperty", text = TUsualKey.NAME,resizable=true)
	})
	@TModelViewType(modelClass=Person.class, modelViewClass=PersonTV.class)
	private ITObservableList<PersonTV> person;

	@TButtonField(labeled = 
			@TLabeled(parse = true, text=TFxKey.BUTTON_REMOVE))
	private SimpleStringProperty remBtn;
	
	public CategoryMV(PersonCategory entity) {
		super(entity);
	}

	/**
	 * @return the code
	 */
	public SimpleStringProperty getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(SimpleStringProperty code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public SimpleStringProperty getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	/**
	 * @return the item
	 */
	public SimpleObjectProperty<PersonTV> getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(SimpleObjectProperty<PersonTV> item) {
		this.item = item;
	}

	/**
	 * @return the addBtn
	 */
	public SimpleStringProperty getAddBtn() {
		return addBtn;
	}

	/**
	 * @param addBtn the addBtn to set
	 */
	public void setAddBtn(SimpleStringProperty addBtn) {
		this.addBtn = addBtn;
	}

	/**
	 * @return the person
	 */
	public ITObservableList<PersonTV> getPerson() {
		return person;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(ITObservableList<PersonTV> person) {
		this.person = person;
	}

	/**
	 * @return the remBtn
	 */
	public SimpleStringProperty getRemBtn() {
		return remBtn;
	}

	/**
	 * @param remBtn the remBtn to set
	 */
	public void setRemBtn(SimpleStringProperty remBtn) {
		this.remBtn = remBtn;
	}

}

/**
 * 
 */
package org.tedros.person.module.category.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TFxKey;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TAutoCompleteEntity;
import org.tedros.fx.annotation.control.TButtonField;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTableColumn;
import org.tedros.fx.annotation.control.TTableView;
import org.tedros.fx.annotation.control.TTableView.TTableViewSelectionModel;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.control.TTrigger;
import org.tedros.fx.annotation.effect.TDropShadow;
import org.tedros.fx.annotation.effect.TEffect;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.form.TSetting;
import org.tedros.fx.annotation.layout.TFieldInset;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THBox.TMargin;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.page.TPage;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TOrder;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.scene.control.TControl;
import org.tedros.fx.annotation.scene.control.TInsets;
import org.tedros.fx.annotation.scene.control.TLabeled;
import org.tedros.fx.annotation.text.TText;
import org.tedros.fx.builder.TRowFactoryWithOpenAction;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.control.TText.TTextStyle;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.person.PersonKeys;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.ejb.controller.IPersonCategoryController;
import org.tedros.person.ejb.controller.IPersonController;
import org.tedros.person.model.Person;
import org.tedros.person.model.PersonCategory;
import org.tedros.person.module.category.trigger.SetCategoryTrigger;
import org.tedros.person.table.PersonTV;
import org.tedros.server.query.TCompareOp;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TSetting(CategorySetting.class)
@TForm(header = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = IPersonCategoryController.JNDI_NAME, model=PersonCategory.class)
@TListViewPresenter(
		page=@TPage(serviceName = IPersonCategoryController.JNDI_NAME,
		query = @TQuery(entity=PersonCategory.class, condition= {
				@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME)},
			orderBy= {@TOrder(label = TUsualKey.NAME, field = "name")}
				),showSearch=true, showOrderBy=true),
		presenter=@TPresenter(decorator = @TDecorator(viewTitle=PersonKeys.VIEW_PERSON_CATEGORY,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false, saveOnlyChangedModels = false, saveAllModels = false)))
@TSecurity(id=DomainApp.PERSON_CATEGORY_FORM_ID, appName = PersonKeys.APP_PERSON,
	moduleName = PersonKeys.MODULE_PERSON_CATEGORIES, viewName = PersonKeys.VIEW_PERSON_CATEGORY,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class CategoryMV extends TEntityModelView<PersonCategory> {

	@TTabPane(
		tabs = { 
			@TTab(text = TUsualKey.MAIN_DATA,fields = {"code", "description"}),
			@TTab(text = TUsualKey.ADDITIONAL_DATA, fields = {"listHeader", "item", "persons"})})
	private SimpleLongProperty id;
	
	@TLabel(text=TUsualKey.CODE)
	@TTextField(maxLength=20,
	node=@TNode(requestFocus=true, parse = true) )
	@THBox(margin=@TMargin(values= {
			@TFieldInset(field="code", insets=@TInsets(top=20)),
			@TFieldInset(field="name", insets=@TInsets(top=20))
			}),	
	pane=@TPane(children={"code", "name"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="code", priority=Priority.NEVER), 
			@TPriority(field="name", priority=Priority.ALWAYS)}))
	private SimpleStringProperty code;
	
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=60, required = true)
	private SimpleStringProperty name;

	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(wrapText=true)
	private SimpleStringProperty description;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id=TFieldBox.TITLE, 
	effect=@TEffect(dropShadow=@TDropShadow, parse = true), parse = true))
	@TText(text=PersonKeys.TEXT_CATEGORY, textAlignment=TextAlignment.LEFT, 
		textStyle=TTextStyle.LARGE)
	private SimpleStringProperty listHeader;

	@TAutoCompleteEntity(modelViewType=PersonTV.class, 
		startSearchAt=2, showMaxItems=30,
		service = IPersonController.JNDI_NAME,
		query = @TQuery(entity = Person.class, 
		condition = {@TCondition(field = "name", operator=TCompareOp.LIKE)}))
	@THBox(	pane=@TPane(children={"item", "addBtn"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="item", priority=Priority.NEVER), 
			@TPriority(field="addBtn", priority=Priority.NEVER)}))
	private SimpleObjectProperty<PersonTV> item;

	@TButtonField(labeled = 
			@TLabeled(parse = true, text=TFxKey.BUTTON_ADD))
	private SimpleStringProperty addBtn;
	
	@TTableView(editable=false,
		selectionModel=@TTableViewSelectionModel(
				selectionMode=SelectionMode.MULTIPLE, parse = true), 
		rowFactory=TRowFactoryWithOpenAction.class,
		control=@TControl(tooltip=TFxKey.TABLE_MENU_TOOLTIP, maxHeight=350, parse = true),
		columns = { 
			@TTableColumn(cellValue="label", text = TUsualKey.NAME)
	})
	@TTrigger(type = SetCategoryTrigger.class)
	@TGenericType(model=Person.class, modelView=PersonTV.class)
	private ITObservableList<PersonTV> persons;

	
	public CategoryMV(PersonCategory entity) {
		super(entity);
		super.formatToString("%s %s", code, name);
		super.registerProperty("item", item);
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
	public ITObservableList<PersonTV> getPersons() {
		return persons;
	}

	/**
	 * @param person the person to set
	 */
	public void setPersons(ITObservableList<PersonTV> person) {
		this.persons = person;
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getDescription() {
		return description;
	}

	public void setDescription(SimpleStringProperty description) {
		this.description = description;
	}

	public SimpleStringProperty getListHeader() {
		return listHeader;
	}

	public void setListHeader(SimpleStringProperty listHeader) {
		this.listHeader = listHeader;
	}
}

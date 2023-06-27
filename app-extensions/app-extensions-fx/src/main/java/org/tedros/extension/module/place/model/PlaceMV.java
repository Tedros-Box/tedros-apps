/**
 * 
 */
package org.tedros.extension.module.place.model;

import static org.tedros.core.annotation.security.TAuthorizationType.DELETE;
import static org.tedros.core.annotation.security.TAuthorizationType.EDIT;
import static org.tedros.core.annotation.security.TAuthorizationType.NEW;
import static org.tedros.core.annotation.security.TAuthorizationType.SAVE;
import static org.tedros.core.annotation.security.TAuthorizationType.VIEW_ACCESS;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.extension.LocatKey;
import org.tedros.extension.domain.DomainApp;
import org.tedros.extension.ejb.controller.IExtensionDomainController;
import org.tedros.extension.ejb.controller.IPlaceController;
import org.tedros.extension.model.Address;
import org.tedros.extension.model.AddressMV;
import org.tedros.extension.model.Contact;
import org.tedros.extension.model.ContactMV;
import org.tedros.extension.model.Place;
import org.tedros.extension.model.PlaceType;
import org.tedros.extension.start.TConstant;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TEditEntityModal;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TProcess;
import org.tedros.fx.annotation.control.TSelectImageField;
import org.tedros.fx.annotation.control.TTab;
import org.tedros.fx.annotation.control.TTabPane;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.layout.THBox;
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
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.domain.TEnvironment;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.server.model.ITFileBaseModel;
import org.tedros.server.query.TCompareOp;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(header = LocatKey.FORM_KEEP_UPDATE, showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = IPlaceController.JNDI_NAME, model=Place.class)
@TListViewPresenter(
	page=@TPage(serviceName = IPlaceController.JNDI_NAME,
	query = @TQuery(entity=Place.class, condition= {
			@TCondition(field = "title", operator=TCompareOp.LIKE, label=TUsualKey.TITLE)},
		orderBy= {@TOrder(label = TUsualKey.TITLE, field = "title")}
			),showSearch=true, showOrderBy=true),
	presenter=@TPresenter(
		decorator = @TDecorator(viewTitle=LocatKey.VIEW_PLACE),
		behavior = @TBehavior(saveOnlyChangedModels=false, saveAllModels=false)))
@TSecurity(id=DomainApp.PLACE_FORM_ID, appName = LocatKey.APP_LOCATION_NAME,
moduleName = LocatKey.MODULE_PLACES, viewName = LocatKey.VIEW_PLACE,
allowedAccesses={VIEW_ACCESS, EDIT, SAVE, DELETE, NEW})
public class PlaceMV extends TEntityModelView<Place> {

	@TTabPane(tabs = { 
		@TTab(text = TUsualKey.MAIN_DATA, fields = {"title", "description"}),
		@TTab(text = TUsualKey.PICTURES, fields = {"pictures"})})
	private SimpleLongProperty id;
	
	@TLabel(text=TUsualKey.TITLE)
	@TTextField(maxLength=60, required = true, node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"title", "type"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="title", priority=Priority.ALWAYS), 
			@TPriority(field="type", priority=Priority.SOMETIMES)}))
	private SimpleStringProperty title;
	
	@TLabel(text=TUsualKey.TYPE)
	@TComboBoxField(required=true,
		process=@TProcess(service = IExtensionDomainController.JNDI_NAME, 
		modelView=PlaceTypeMV.class, query=@TQuery(entity=PlaceType.class)))
	private SimpleObjectProperty<PlaceType> type;
	
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(maxLength=500, wrapText=true, prefRowCount=7)
	@THBox(	pane=@TPane(children={"description","address", "contacts"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="description", priority=Priority.ALWAYS), 
			@TPriority(field="address", priority=Priority.NEVER), 
			@TPriority(field="contacts", priority=Priority.NEVER)}))
	private SimpleStringProperty description;
	
	@TLabel(text=TUsualKey.ADDRESS)
	@TEditEntityModal(model = Address.class, modelView=AddressMV.class, required=true)
	@TGenericType(model = Address.class, modelView=AddressMV.class)
	private SimpleObjectProperty<AddressMV> address;
	
	@TLabel(text=TUsualKey.CONTACTS)
	@TEditEntityModal(model = Contact.class, modelView=ContactMV.class)
	@TGenericType(model = Contact.class, modelView=ContactMV.class)
	private ITObservableList<ContactMV> contacts;
	
	@TFieldBox(node=@TNode(id="img", parse = true))
	@TSelectImageField(source=TEnvironment.LOCAL, target=TEnvironment.REMOTE, remoteOwner=TConstant.UUI)
	@TGenericType(model = TFileEntity.class)
	private ITObservableList<ITFileBaseModel> pictures;
	
	public PlaceMV(Place entity) {
		super(entity);
	}

	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public ITObservableList<ContactMV> getContacts() {
		return contacts;
	}

	public void setContacts(ITObservableList<ContactMV> contacts) {
		this.contacts = contacts;
	}

	@Override
	public SimpleStringProperty toStringProperty() {
		return title;
	}

	public SimpleStringProperty getTitle() {
		return title;
	}

	public void setTitle(SimpleStringProperty title) {
		this.title = title;
	}

	public SimpleStringProperty getDescription() {
		return description;
	}

	public void setDescription(SimpleStringProperty description) {
		this.description = description;
	}

	public SimpleObjectProperty<AddressMV> getAddress() {
		return address;
	}

	public void setAddress(SimpleObjectProperty<AddressMV> address) {
		this.address = address;
	}

	public ITObservableList<ITFileBaseModel> getPictures() {
		return pictures;
	}

	public void setPictures(ITObservableList<ITFileBaseModel> pictures) {
		this.pictures = pictures;
	}

	public SimpleObjectProperty<PlaceType> getType() {
		return type;
	}

	public void setType(SimpleObjectProperty<PlaceType> type) {
		this.type = type;
	}


}

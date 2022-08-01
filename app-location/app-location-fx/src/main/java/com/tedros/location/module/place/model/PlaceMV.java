/**
 * 
 */
package com.tedros.location.module.place.model;

import static com.tedros.core.annotation.security.TAuthorizationType.DELETE;
import static com.tedros.core.annotation.security.TAuthorizationType.EDIT;
import static com.tedros.core.annotation.security.TAuthorizationType.NEW;
import static com.tedros.core.annotation.security.TAuthorizationType.SAVE;
import static com.tedros.core.annotation.security.TAuthorizationType.VIEW_ACCESS;

import com.tedros.common.model.TFileEntity;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.ejb.base.model.ITFileBaseModel;
import com.tedros.ejb.controller.IPlaceController;
import com.tedros.ejb.controller.IPlaceTypeController;
import com.tedros.extension.contact.model.ContactMV;
import com.tedros.extension.model.Contact;
import com.tedros.extension.start.TConstant;
import com.tedros.fxapi.TUsualKey;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TContent;
import com.tedros.fxapi.annotation.control.TEditEntityModal;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.annotation.control.TOptionsList;
import com.tedros.fxapi.annotation.control.TSelectImageField;
import com.tedros.fxapi.annotation.control.TTab;
import com.tedros.fxapi.annotation.control.TTabPane;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TDetailForm;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.view.TOption;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.domain.TEnvironment;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.location.LocatKey;
import com.tedros.location.domain.DomainApp;
import com.tedros.location.model.Address;
import com.tedros.location.model.Place;
import com.tedros.location.model.PlaceType;
import com.tedros.location.module.address.model.AddressMV;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = LocatKey.FORM_KEEP_UPDATE, showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = IPlaceController.JNDI_NAME, model=Place.class)
@TListViewPresenter(
	paginator=@TPaginator(entityClass = Place.class, 
		serviceName = IPlaceController.JNDI_NAME,
		show=true, showSearchField=true, searchFieldName="title", 
		orderBy = {	@TOption(text = TUsualKey.TITLE, value = "title")}),
	presenter=@TPresenter(
		decorator = @TDecorator(viewTitle=LocatKey.VIEW_PLACE),
		behavior = @TBehavior(saveOnlyChangedModels=false, saveAllModels=false)))
@TSecurity(id=DomainApp.PLACE_FORM_ID, appName = LocatKey.APP_LOCATION_NAME,
moduleName = LocatKey.MODULE_ADMINISTRATIVE, viewName = LocatKey.VIEW_PLACE,
allowedAccesses={VIEW_ACCESS, EDIT, SAVE, DELETE, NEW})
public class PlaceMV extends TEntityModelView<Place> {

	@TTabPane(
	tabs = { 
			@TTab(text = TUsualKey.MAIN_DATA, 
				content = @TContent(detailForm=@TDetailForm(fields = {"title", "description"}))),
			@TTab(text = TUsualKey.PICTURES, 
				content = @TContent(detailForm=@TDetailForm(fields = {"pictures"}))) })
	private SimpleLongProperty id;
	
	@TLabel(text=TUsualKey.TITLE)
	@TTextField(maxLength=60, required = true, node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"title", "type"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="title", priority=Priority.ALWAYS), 
			@TPriority(field="type", priority=Priority.SOMETIMES)}))
	private SimpleStringProperty title;
	
	@TLabel(text=TUsualKey.TYPE)
	@TComboBoxField(firstItemTex=TUsualKey.SELECT, required=true,
		optionsList=@TOptionsList(serviceName = IPlaceTypeController.JNDI_NAME, 
		optionModelViewClass=PlaceTypeMV.class,
		entityClass=PlaceType.class))
	private SimpleObjectProperty<PlaceType> type;
	
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(maxLength=500, wrapText=true, prefRowCount=7)
	@THBox(	pane=@TPane(children={"description","address", "contacts"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="description", priority=Priority.ALWAYS), 
			@TPriority(field="address", priority=Priority.NEVER), 
			@TPriority(field="contacts", priority=Priority.NEVER)}))
	private SimpleStringProperty description;
	
	@TLabel(text=TUsualKey.ADDRESS)
	@TEditEntityModal(modelClass = Address.class, modelViewClass=AddressMV.class, required=true)
	@TModelViewType(modelClass = Address.class, modelViewClass=AddressMV.class)
	private SimpleObjectProperty<AddressMV> address;
	
	@TLabel(text=TUsualKey.CONTACTS)
	@TEditEntityModal(modelClass = Contact.class, modelViewClass=ContactMV.class)
	@TModelViewType(modelClass = Contact.class, modelViewClass=ContactMV.class)
	private ITObservableList<ContactMV> contacts;
	
	@TFieldBox(node=@TNode(id="img", parse = true))
	@TSelectImageField(source=TEnvironment.LOCAL, target=TEnvironment.REMOTE, remoteOwner=TConstant.UUI)
	@TModelViewType(modelClass = TFileEntity.class)
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
	public SimpleStringProperty getDisplayProperty() {
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

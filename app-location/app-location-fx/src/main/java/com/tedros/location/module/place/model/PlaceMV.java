/**
 * 
 */
package com.tedros.location.module.place.model;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.extension.contact.model.ContactMV;
import com.tedros.extension.model.Contact;
import com.tedros.fxapi.annotation.control.TEditEntityModal;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TListViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.view.TOption;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.location.domain.DomainApp;
import com.tedros.location.model.Place;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = "#{form.keep.update}", showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = "IPlaceControllerRemote", model=Place.class)
@TListViewPresenter(
	paginator=@TPaginator(entityClass = Place.class, serviceName = "IPlaceControllerRemote",
			show=true, showSearchField=true, searchFieldName="title", 
			orderBy = {	@TOption(text = "#{label.title}", value = "title")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle="#{view.place}"),
	behavior=@TBehavior(runNewActionAfterSave=true)))
@TSecurity(	id=DomainApp.PLACE_FORM_ID, 
	appName = "#{app.location.name}", moduleName = "#{module.administrative}", viewName = "#{view.place}",
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class PlaceMV extends TEntityModelView<Place> {

	private SimpleLongProperty id;
	
	@TReaderHtml
	@TLabel(text="#{label.title}")
	@TTextField(maxLength=2, required = true, node=@TNode(requestFocus=true, parse = true))
	private SimpleStringProperty title;
	
	//private String description;
	
	//private Address address;
	
	@TEditEntityModal(modelClass = Contact.class, modelViewClass=ContactMV.class)
	@TModelViewType(modelClass = Contact.class, modelViewClass=ContactMV.class)
	public ITObservableList<ContactMV> contacts;
	
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

}

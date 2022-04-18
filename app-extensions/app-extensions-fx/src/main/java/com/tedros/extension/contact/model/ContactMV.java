/**
 * 
 */
package com.tedros.extension.contact.model;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.extension.domain.DomainApp;
import com.tedros.extension.model.Contact;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TEditModalPresenter;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TEditModalPresenter()
@TSecurity(	id=DomainApp.CONTACT_FORM_ID, 
appName = "#{app.location.name}", moduleName = "#{module.administrative}", viewName = "#{view.place}",
allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
				TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class ContactMV extends TEntityModelView<Contact> {

	private SimpleLongProperty id;
	
	@TReaderHtml
	@TLabel(text="#{label.type}")
	@TTextField(maxLength=60, required = true, node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"type", "value"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="type", priority=Priority.ALWAYS), 
			@TPriority(field="value", priority=Priority.ALWAYS)}))
	private SimpleStringProperty type;
	
	@TReaderHtml
	@TLabel(text="#{label.value}")
	@TTextField(maxLength=120, required = true)
	private SimpleStringProperty value;
	
	@TReaderHtml
	@TLabel(text="#{label.observation}")
	@TTextAreaField(maxLength=250)
	private SimpleStringProperty observation;

	public ContactMV(Contact entity) {
		super(entity);
	}
	
	public SimpleLongProperty getId() {
		return id;
	}

	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getType() {
		return type;
	}

	public void setType(SimpleStringProperty type) {
		this.type = type;
	}

	public SimpleStringProperty getValue() {
		return value;
	}

	public void setValue(SimpleStringProperty value) {
		this.value = value;
	}

	public SimpleStringProperty getObservation() {
		return observation;
	}

	public void setObservation(SimpleStringProperty observation) {
		this.observation = observation;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return value;
	}
	
}

/**
 * 
 */
package com.tedros.extension.contact.model;

import com.tedros.extension.ejb.controller.IContactController;
import com.tedros.extension.model.Contact;
import com.tedros.extension.model.ContactType;
import com.tedros.fxapi.annotation.control.TComboBoxField;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TEditModalPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TEditModalPresenter()
@TEjbService(model = Contact.class, serviceName = IContactController.JNDI_NAME)
public class ContactMV extends TEntityModelView<Contact> {

	private SimpleLongProperty id;
	
	@TReaderHtml
	@TLabel(text="#{label.name}")
	@TTextField(maxLength=60, required = true, node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"name", "type", "value"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="type", priority=Priority.ALWAYS), 
			@TPriority(field="value", priority=Priority.ALWAYS), 
			@TPriority(field="name", priority=Priority.ALWAYS)}))
	private SimpleStringProperty name;
	
	@TReaderHtml
	@TLabel(text="#{label.type}")
	@TComboBoxField(firstItemTex="#{label.select}", items=TypeItemBuiler.class)
	private SimpleObjectProperty<ContactType> type;
	
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
		return name;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public void setType(SimpleObjectProperty<ContactType> type) {
		this.type = type;
	}

	public SimpleObjectProperty<ContactType> getType() {
		return type;
	}
	
}

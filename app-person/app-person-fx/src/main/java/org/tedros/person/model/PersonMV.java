/**
 * 
 */
package org.tedros.person.model;

import org.tedros.extension.model.Address;
import org.tedros.extension.model.AddressMV;
import org.tedros.extension.model.Contact;
import org.tedros.extension.model.ContactMV;
import org.tedros.extension.model.Document;
import org.tedros.extension.model.ModalDocumentMV;
import org.tedros.extension.module.doc.trigger.DocumentTrigger;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TDetailListField;
import org.tedros.fx.annotation.control.TEditEntityModal;
import org.tedros.fx.annotation.control.TFieldBox;
import org.tedros.fx.annotation.control.TGenericType;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.control.TTrigger;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.collections.ITObservableList;
import org.tedros.fx.model.TEntityModelView;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */

public class PersonMV<P extends Person> extends TEntityModelView<P> {

	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=120, 
		node=@TNode(requestFocus=true, parse = true))
	protected SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.ADDRESS)
	@TEditEntityModal(model = Address.class, modelView=AddressMV.class)
	@TGenericType(model = Address.class, modelView=AddressMV.class)
	@THBox(	pane=@TPane(children={"address", "contacts", "documents", "attributes"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="address", priority=Priority.ALWAYS), 
			@TPriority(field="contacts", priority=Priority.ALWAYS), 
			@TPriority(field="attributes", priority=Priority.ALWAYS), 
			@TPriority(field="documents", priority=Priority.ALWAYS)}))
	protected SimpleObjectProperty<AddressMV> address;
	
	@TLabel(text=TUsualKey.ATTRIBUTES)
	@TEditEntityModal(modalHeight=400, modalWidth=600,
	model = PersonAttributes.class, modelView=PersonAttributesMV.class)
	@TGenericType(model = PersonAttributes.class, modelView=PersonAttributesMV.class)
	protected ITObservableList<PersonAttributesMV> attributes;
	
	@TLabel(text=TUsualKey.CONTACTS)
	@TEditEntityModal(modalHeight=400, modalWidth=600,
	model = Contact.class, modelView=ContactMV.class)
	@TGenericType(model = Contact.class, modelView=ContactMV.class)
	protected ITObservableList<ContactMV> contacts;
	
	@TLabel(text=TUsualKey.DOCUMENTS)
	@TEditEntityModal(modalHeight=490, modalWidth=700,
	model = Document.class, modelView=ModalDocumentMV.class)
	@TTrigger(type = DocumentTrigger.class)
	@TGenericType(model=Document.class, modelView=ModalDocumentMV.class)
	protected ITObservableList<ModalDocumentMV> documents;

	@TFieldBox(node=@TNode(id="evdtl", parse = true))
	@TDetailListField(modelView = PersonEventMV.class, entity = PersonEvent.class)
	@TGenericType(model=PersonEvent.class, modelView=PersonEventMV.class)
	protected ITObservableList<PersonEventMV> events;

	@TTextAreaField(maxLength=2000, wrapText=true)
	protected SimpleStringProperty description;
	
	@TTextAreaField(maxLength=2000, wrapText=true)
	protected SimpleStringProperty observation;
	
	public PersonMV(P entity) {
		super(entity);
	}
	

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}


	public SimpleObjectProperty<AddressMV> getAddress() {
		return address;
	}


	public void setAddress(SimpleObjectProperty<AddressMV> address) {
		this.address = address;
	}


	public ITObservableList<PersonAttributesMV> getAttributes() {
		return attributes;
	}


	public void setAttributes(ITObservableList<PersonAttributesMV> attributes) {
		this.attributes = attributes;
	}


	public ITObservableList<ContactMV> getContacts() {
		return contacts;
	}


	public void setContacts(ITObservableList<ContactMV> contacts) {
		this.contacts = contacts;
	}


	public ITObservableList<ModalDocumentMV> getDocuments() {
		return documents;
	}


	public void setDocuments(ITObservableList<ModalDocumentMV> documents) {
		this.documents = documents;
	}


	public ITObservableList<PersonEventMV> getEvents() {
		return events;
	}


	public void setEvents(ITObservableList<PersonEventMV> events) {
		this.events = events;
	}


	public SimpleStringProperty getDescription() {
		return description;
	}


	public void setDescription(SimpleStringProperty description) {
		this.description = description;
	}


	public SimpleStringProperty getObservation() {
		return observation;
	}


	public void setObservation(SimpleStringProperty observation) {
		this.observation = observation;
	}

}

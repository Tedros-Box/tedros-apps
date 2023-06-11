/**
 * 
 */
package org.tedros.extension.model;

import org.tedros.extension.ejb.controller.IContactController;
import org.tedros.extension.model.Contact;
import org.tedros.extension.model.ContactType;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TComboBoxField;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TEditModalPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.reader.TReaderHtml;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.model.TEntityModelView;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TEditModalPresenter(listViewMaxWidth=150, listViewMinWidth=150)
@TEjbService(model = Contact.class, serviceName = IContactController.JNDI_NAME)
public class ContactMV extends TEntityModelView<Contact> {

	@TReaderHtml
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=60, required = true, node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"name", "type", "value"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="type", priority=Priority.ALWAYS), 
			@TPriority(field="value", priority=Priority.ALWAYS), 
			@TPriority(field="name", priority=Priority.ALWAYS)}))
	private SimpleStringProperty name;
	
	@TReaderHtml
	@TLabel(text=TUsualKey.TYPE)
	@TComboBoxField(items=TypeItemBuiler.class)
	private SimpleObjectProperty<ContactType> type;
	
	@TReaderHtml
	@TLabel(text=TUsualKey.VALUE)
	@TTextField(maxLength=120, required = true)
	private SimpleStringProperty value;
	
	@TReaderHtml
	@TLabel(text=TUsualKey.OBSERVATION)
	@TTextAreaField(maxLength=250)
	private SimpleStringProperty observation;

	public ContactMV(Contact entity) {
		super(entity);
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
	public SimpleStringProperty toStringProperty() {
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

/**
 * 
 */
package com.tedros.person.module.natural.model;

import com.tedros.fxapi.TUsualKey;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TEditModalPresenter;
import com.tedros.fxapi.annotation.process.TEjbService;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.person.ejb.controller.IPersonAttributesController;
import com.tedros.person.model.PersonAttributes;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TEditModalPresenter(listViewMaxWidth=150, listViewMinWidth=150)
@TEjbService(model = PersonAttributes.class, serviceName = IPersonAttributesController.JNDI_NAME)
public class PersonAttributesMV extends TEntityModelView<PersonAttributes> {


	private SimpleLongProperty id;
	
	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=120, required = true, node=@TNode(requestFocus=true, parse = true))
	@THBox(	pane=@TPane(children={"name", "value"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="value", priority=Priority.ALWAYS), 
			@TPriority(field="name", priority=Priority.ALWAYS)}))
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.VALUE)
	@TTextField(maxLength=120, required = true)
	private SimpleStringProperty value;
	
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(maxLength=1024, wrapText=true)
	private SimpleStringProperty description;
	
	
	public PersonAttributesMV(PersonAttributes entity) {
		super(entity);
	}


	public SimpleLongProperty getId() {
		return id;
	}


	public void setId(SimpleLongProperty id) {
		this.id = id;
	}


	public SimpleStringProperty getDisplayProperty() {
		return name;
	}


	public SimpleStringProperty getName() {
		return name;
	}


	public void setName(SimpleStringProperty name) {
		this.name = name;
	}


	public SimpleStringProperty getValue() {
		return value;
	}


	public void setValue(SimpleStringProperty value) {
		this.value = value;
	}


	public SimpleStringProperty getDescription() {
		return description;
	}


	public void setDescription(SimpleStringProperty description) {
		this.description = description;
	}


}

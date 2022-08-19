/**
 * 
 */
package org.tedros.person.module.natural.model;

import org.tedros.person.ejb.controller.IPersonAttributesController;
import org.tedros.person.model.PersonAttributes;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.layout.THBox;
import org.tedros.fx.annotation.layout.THGrow;
import org.tedros.fx.annotation.layout.TPane;
import org.tedros.fx.annotation.layout.TPriority;
import org.tedros.fx.annotation.presenter.TEditModalPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.presenter.model.TEntityModelView;

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

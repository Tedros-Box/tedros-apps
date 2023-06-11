/**
 * 
 */
package org.tedros.samples.model;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.sample.entity.GenericDomain;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis
 *
 */public class GenericDomainMV<M extends GenericDomain> extends TEntityModelView<M> {

	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=25, required = true, 
	node=@TNode(requestFocus=true, parse = true) )
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(maxLength=250, wrapText=true, prefRowCount=4)
	private SimpleStringProperty description;
	
	public GenericDomainMV(M entity) {
		super(entity);
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

	public SimpleStringProperty getDescription() {
		return description;
	}

	public void setDescription(SimpleStringProperty description) {
		this.description = description;
	}
}

/**
 * 
 */
package org.tedros.extension.model;

import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.model.TEntityModelView;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class ExtensionDomainMV<E extends ExtensionDomain> extends TEntityModelView<E> {

	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=60, required = true, 
	node=@TNode(requestFocus=true, parse = true))
	protected SimpleStringProperty name;
	
	public ExtensionDomainMV(E entity) {
		super(entity);
	}

}

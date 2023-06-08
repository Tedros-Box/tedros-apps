/**
 * 
 */
package org.tedros.stock.module.inventory.builder;

import org.tedros.fx.builder.TGenericBuilder;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.person.model.LegalPerson;

import javafx.beans.property.ObjectProperty;

/**
 * @author Davis Gordon
 *
 */
public class LegalPersonValBuilder extends TGenericBuilder<LegalPerson> {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public LegalPerson build() {
		TEntityModelView mv = (TEntityModelView) super.getComponentDescriptor().getModelView();
		ObjectProperty<LegalPerson> ob = (ObjectProperty<LegalPerson>) mv.getProperty("legalPerson");
		return ob!=null 
				? ob.getValue()
						: null;
	}

}

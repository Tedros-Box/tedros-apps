/**
 * 
 */
package org.tedros.stock.module.inventory.builder;

import org.tedros.fx.builder.TGenericBuilder;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.person.model.CostCenter;
import org.tedros.person.model.LegalPerson;

import javafx.beans.property.ObjectProperty;

/**
 * @author Davis Gordon
 *
 */
public class CostCenterValBuilder extends TGenericBuilder<CostCenter> {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public CostCenter build() {
		TEntityModelView mv = (TEntityModelView) super.getComponentDescriptor().getModelView();
		ObjectProperty<CostCenter> ob = (ObjectProperty<CostCenter>) mv.getProperty("costCenter");
		return ob!=null 
				? ob.getValue()
						: null;
	}

}

/**
 * 
 */
package org.tedros.samples.module.forms;

import org.tedros.core.TModule;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.samples.module.forms.model.AdvancedControlMV;
import org.tedros.samples.module.forms.model.FieldModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Davis Gordon
 *
 */
public class AdvancedControlModule extends TModule {

	/* (non-Javadoc)
	 * @see org.tedros.core.TModule#tStart()
	 */
	@Override
	public void tStart() {
		ObservableList<AdvancedControlMV> l0 = FXCollections.observableArrayList(new AdvancedControlMV(new FieldModel()));
		super.tShowView(new TDynaView<AdvancedControlMV>(AdvancedControlMV.class, l0));
	}

}

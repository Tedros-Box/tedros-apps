/**
 * 
 */
package org.tedros.samples.module.forms;

import org.tedros.core.TModule;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.samples.module.forms.model.FieldModel;
import org.tedros.samples.module.forms.model.FileControlMV;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Davis Gordon
 *
 */
public class FileControlModule extends TModule {

	/* (non-Javadoc)
	 * @see org.tedros.core.TModule#tStart()
	 */
	@Override
	public void tStart() {
		ObservableList<FileControlMV> l0 = FXCollections.observableArrayList(new FileControlMV(new FieldModel()));
		super.tShowView(new TDynaView<FileControlMV>(FileControlMV.class, l0));
	}

}

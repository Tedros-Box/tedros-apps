/**
 * 
 */
package org.tedros.samples.module.control;

import org.tedros.core.TModule;
import org.tedros.fx.presenter.dynamic.view.TDynaGroupView;
import org.tedros.fx.presenter.view.group.TGroupPresenter;
import org.tedros.fx.presenter.view.group.TGroupView;
import org.tedros.fx.presenter.view.group.TViewItem;
import org.tedros.samples.module.control.model.AdvancedControlMV;
import org.tedros.samples.module.control.model.BasicControlMV;
import org.tedros.samples.module.control.model.FileControlMV;
import org.tedros.samples.module.control.model.ReaderControlMV;
import org.tedros.samples.module.control.model.WebControlMV;

/**
 * @author Davis Gordon
 *
 */
public class ControlSampleModule extends TModule {

	/* (non-Javadoc)
	 * @see org.tedros.core.TModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TGroupView<TGroupPresenter>(this, "Control Samples", 
				new TViewItem(TDynaGroupView.class, BasicControlMV.class, "Basic Controls"),
				new TViewItem(TDynaGroupView.class, AdvancedControlMV.class, "Advanced Controls"),
				new TViewItem(TDynaGroupView.class, FileControlMV.class, "File Controls"),
				new TViewItem(TDynaGroupView.class, WebControlMV.class, "Web Controls"),
				new TViewItem(TDynaGroupView.class, ReaderControlMV.class, "Reader Controls")
				));;
	}

}

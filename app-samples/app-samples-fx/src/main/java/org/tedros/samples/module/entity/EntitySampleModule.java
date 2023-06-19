/**
 * 
 */
package org.tedros.samples.module.entity;

import org.tedros.core.TModule;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.presenter.dynamic.view.TDynaGroupView;
import org.tedros.fx.presenter.view.group.TGroupPresenter;
import org.tedros.fx.presenter.view.group.TGroupView;
import org.tedros.fx.presenter.view.group.TViewItem;
import org.tedros.sample.domain.DomainApp;
import org.tedros.samples.SmplsKey;
import org.tedros.samples.module.entity.model.MasterDetailMV;

/**
 * @author Davis Gordon
 *
 */

@TSecurity(id=DomainApp.SAMPLE_A_MODULE_ID, 
appName = SmplsKey.APP_SAMPLES, 
moduleName = "Samples", 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class EntitySampleModule extends TModule {

	/* (non-Javadoc)
	 * @see org.tedros.core.TModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TGroupView<TGroupPresenter>(this, "Entity Samples", 
				new TViewItem(TDynaGroupView.class, MasterDetailMV.class, "Edit master/detail entity")/*,
				new TViewItem(TDynaGroupView.class, AdvancedControlMV.class, "Advanced Controls"),
				new TViewItem(TDynaGroupView.class, FileControlMV.class, "File Controls"),
				new TViewItem(TDynaGroupView.class, WebControlMV.class, "Web Controls"),
				new TViewItem(TDynaGroupView.class, ReaderControlMV.class, "Reader Controls")*/
				));;
	}

}

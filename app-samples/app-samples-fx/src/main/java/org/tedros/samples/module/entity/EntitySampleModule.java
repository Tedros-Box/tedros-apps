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
import org.tedros.samples.module.entity.model.CustomActionMV;
import org.tedros.samples.module.entity.model.IntegratedMV;
import org.tedros.samples.module.entity.model.MasterDetailMV;
import org.tedros.samples.module.entity.model.PageSearchMV;

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
				new TViewItem(TDynaGroupView.class, MasterDetailMV.class, "Edit master/detail entity"),
				new TViewItem(TDynaGroupView.class, PageSearchMV.class, "Page and search entities"),
				new TViewItem(TDynaGroupView.class, IntegratedMV.class, "Integrated entity sample"),
				new TViewItem(TDynaGroupView.class, CustomActionMV.class, "Custom action sample")/*,
				new TViewItem(TDynaGroupView.class, ReaderControlMV.class, "Reader Controls")*/
				));;
	}

}

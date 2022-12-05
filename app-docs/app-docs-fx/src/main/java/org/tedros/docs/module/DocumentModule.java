/**
 * 
 */
package org.tedros.docs.module;

import org.tedros.core.TModule;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.docs.domain.DomainApp;
import org.tedros.docs.module.model.DocumentMV;
import org.tedros.docs.module.model.DocumentStateMV;
import org.tedros.docs.module.model.DocumentTypeMV;
import org.tedros.fx.presenter.dynamic.view.TDynaGroupView;
import org.tedros.fx.presenter.view.group.TGroupPresenter;
import org.tedros.fx.presenter.view.group.TGroupView;
import org.tedros.fx.presenter.view.group.TViewItem;
/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id=DomainApp.DOCUMENT_MODULE_ID, appName = "#{app.docs}", 
		 moduleName = "#{module.docs}", 
		 allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class DocumentModule extends TModule {


	/* (non-Javadoc)
	 * @see org.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TGroupView<TGroupPresenter>(this, "#{view.group.docs}", 
				new TViewItem(TDynaGroupView.class, DocumentMV.class, "#{view.docs}", true), 
				new TViewItem(TDynaGroupView.class, DocumentTypeMV.class, "#{view.docs.type}"), 
				new TViewItem(TDynaGroupView.class, DocumentStateMV.class, "#{view.docs.state}")
				));
	}

}

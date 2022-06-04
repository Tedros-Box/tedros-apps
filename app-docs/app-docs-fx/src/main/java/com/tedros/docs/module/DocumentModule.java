/**
 * 
 */
package com.tedros.docs.module;

import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.docs.domain.DomainApp;
import com.tedros.docs.module.model.DocumentMV;
import com.tedros.docs.module.model.DocumentStateMV;
import com.tedros.docs.module.model.DocumentTypeMV;
import com.tedros.fxapi.presenter.dynamic.view.TDynaGroupView;
import com.tedros.fxapi.presenter.view.group.TGroupPresenter;
import com.tedros.fxapi.presenter.view.group.TGroupView;
import com.tedros.fxapi.presenter.view.group.TViewItem;
/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id=DomainApp.DOCUMENT_MODULE_ID, appName = "#{app.docs}", 
		 moduleName = "#{module.docs}", 
		 allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class DocumentModule extends TModule {


	/* (non-Javadoc)
	 * @see com.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TGroupView<TGroupPresenter>(this, "#{view.docs}", 
				new TViewItem(TDynaGroupView.class, DocumentMV.class, "#{view.docs}", true), 
				new TViewItem(TDynaGroupView.class, DocumentTypeMV.class, "#{view.docs.type}"), 
				new TViewItem(TDynaGroupView.class, DocumentStateMV.class, "#{view.docs.state}")
				));
	}

}

/**
 * 
 */
package org.tedros.extension.module.doc;

import org.tedros.core.TModule;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.extension.ExtKey;
import org.tedros.extension.domain.DomainApp;
import org.tedros.extension.module.doc.model.DocumentMV;
import org.tedros.extension.module.doc.model.DocumentStatusMV;
import org.tedros.extension.module.doc.model.DocumentTypeMV;
import org.tedros.fx.presenter.dynamic.view.TDynaGroupView;
import org.tedros.fx.presenter.view.group.TGroupPresenter;
import org.tedros.fx.presenter.view.group.TGroupView;
import org.tedros.fx.presenter.view.group.TViewItem;
/**
 * @author Davis Gordon
 *
 */
@TSecurity(	id=DomainApp.DOCUMENT_MODULE_ID, appName = ExtKey.APP_EXTS, 
		 moduleName = ExtKey.MODULE_DOCS, 
		 allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class DocumentModule extends TModule {


	/* (non-Javadoc)
	 * @see org.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TGroupView<TGroupPresenter>(this, ExtKey.VIEW_GROUP_DOCS, 
				new TViewItem(TDynaGroupView.class, DocumentMV.class, ExtKey.VIEW_DOCS, true), 
				new TViewItem(TDynaGroupView.class, DocumentTypeMV.class, ExtKey.VIEW_DOCS_TYPE), 
				new TViewItem(TDynaGroupView.class, DocumentStatusMV.class, ExtKey.VIEW_DOCS_STATE)
				));
	}

}

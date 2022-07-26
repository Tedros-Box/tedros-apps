/**
 * 
 */
package com.template.module.mymodule;

import com.tedros.core.TModule;
import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.template.TMP_Key;
import com.template.domain.DomainApp;
import com.template.module.mymodule.model._MyEntity_MV;

/**
 * @author myname
 *
 */
@TSecurity(id=DomainApp.MY_ENTITY__MODULE_ID, 
appName = TMP_Key.APP_MY_APP, 
moduleName = TMP_Key.MODULE_MY_APP, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class _MyEntity_Module extends TModule {

	/* (non-Javadoc)
	 * @see com.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TDynaView<>(this, _MyEntity_MV.class));

	}

}

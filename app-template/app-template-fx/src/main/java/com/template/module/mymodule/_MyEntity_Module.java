/**
 * 
 */
package com.template.module.mymodule;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;

import com.template.TMP_Key;
import com.template.domain.DomainApp;
import com.template.entity._MyEntity_;
import com.template.module.mymodule.model._MyEntity_MV;

/**
 * @author myname
 *
 */
@TView(items = { 
	@TItem(title = TMP_Key.MY_APP_MY_VIEW, 
			modelView=_MyEntity_MV.class, model=_MyEntity_.class)})
@TSecurity(id=DomainApp.MY_ENTITY__MODULE_ID, 
appName = TMP_Key.APP_MY_APP, 
moduleName = TMP_Key.MODULE_MY_APP, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class _MyEntity_Module extends TModule {

}

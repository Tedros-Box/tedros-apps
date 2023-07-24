/**
 * 
 */
package org.tedros.services.module.service;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.services.ServKey;
import org.tedros.services.domain.DomainApp;
import org.tedros.services.model.Contract;
import org.tedros.services.model.Service;
import org.tedros.services.model.ServiceLocation;
import org.tedros.services.model.ServiceType;
import org.tedros.services.module.service.model.ContractMV;
import org.tedros.services.module.service.model.ServiceLocationMV;
import org.tedros.services.module.service.model.ServiceMV;
import org.tedros.services.module.service.model.ServiceTypeMV;

/**
 * @author Davis Gordon
 *
 */
@TView(title=ServKey.MODULE_SERVICES, 
	items = { 
		@TItem(title = ServKey.VIEW_SERVICE_TYPE, 
		description=ServKey.VIEW_SERVICE_TYPE_DESC,
		model=ServiceType.class, modelView=ServiceTypeMV.class),
		@TItem(title = ServKey.VIEW_SERVICE_LOCATION, 
		description=ServKey.VIEW_SERVICE_LOCATION_DESC,
		model=ServiceLocation.class, modelView=ServiceLocationMV.class),
		@TItem(title = ServKey.VIEW_SERVICE, 
		description=ServKey.VIEW_SERVICE_DESC,
		model=Service.class, modelView=ServiceMV.class),
		@TItem(title = ServKey.VIEW_CONTRACT, 
		description=ServKey.VIEW_CONTRACT_DESC,
		model=Contract.class, modelView=ContractMV.class) 
})
@TSecurity(id=DomainApp.SERVICE_MODULE_ID, 
appName = ServKey.APP_SERVICE, 
moduleName = ServKey.MODULE_SERVICES, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class ServiceModule extends TModule {

}

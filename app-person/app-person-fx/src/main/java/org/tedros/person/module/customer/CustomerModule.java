/**
 * 
 */
package org.tedros.person.module.customer;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TLoadable;
import org.tedros.core.annotation.TModel;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.person.PersonKeys;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.model.Customer;
import org.tedros.person.module.customer.model.CustomerMV;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(id=DomainApp.CUSTOMER_MODULE_ID, 
appName = PersonKeys.APP_PERSON, 
moduleName = PersonKeys.MODULE_CUSTOMER, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
@TLoadable({
	@TModel(modelType = Customer.class, modelViewType=CustomerMV.class, moduleType=CustomerModule.class)
})
public class CustomerModule extends TModule {

	/* (non-Javadoc)
	 * @see org.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TDynaView<CustomerMV>(this, CustomerMV.class));
	}
}

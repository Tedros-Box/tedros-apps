/**
 * 
 */
package org.tedros.person.module.customer;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TLoadable;
import org.tedros.core.annotation.TModel;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.presenter.dynamic.view.TDynaGroupView;
import org.tedros.fx.presenter.view.group.TGroupPresenter;
import org.tedros.fx.presenter.view.group.TGroupView;
import org.tedros.fx.presenter.view.group.TViewItem;
import org.tedros.person.PersonKeys;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.model.ClientCompany;
import org.tedros.person.model.Customer;
import org.tedros.person.module.customer.model.ClientCompanyMV;
import org.tedros.person.module.customer.model.ClientCompanyStatusMV;
import org.tedros.person.module.customer.model.ClientCompanyTypeMV;
import org.tedros.person.module.customer.model.CustomerMV;
import org.tedros.person.module.customer.model.CustomerStatusMV;
import org.tedros.person.module.customer.model.CustomerTypeMV;

/**
 * @author Davis Gordon
 *
 */
@TSecurity(id=DomainApp.CUSTOMER_MODULE_ID, 
appName = PersonKeys.APP_PERSON, 
moduleName = PersonKeys.MODULE_CUSTOMER, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
@TLoadable({
	@TModel(modelType = Customer.class,
		modelViewType=CustomerMV.class, 
		moduleType=CustomerModule.class),
	@TModel(modelType = ClientCompany.class,
		modelViewType=ClientCompanyMV.class, 
		moduleType=CustomerModule.class)
})
public class CustomerModule extends TModule {

	/* (non-Javadoc)
	 * @see org.tedros.core.ITModule#tStart()
	 */
	@Override
	public void tStart() {
		super.tShowView(new TGroupView<TGroupPresenter>(this, PersonKeys.MODULE_CUSTOMER, 
				new TViewItem(TDynaGroupView.class, CustomerMV.class, PersonKeys.VIEW_CUSTOMERS),
				new TViewItem(TDynaGroupView.class, CustomerTypeMV.class, PersonKeys.VIEW_CUSTOMER_TYPE), 
				new TViewItem(TDynaGroupView.class, CustomerStatusMV.class, PersonKeys.VIEW_CUSTOMER_STATUS), 
				new TViewItem(TDynaGroupView.class, ClientCompanyMV.class, PersonKeys.VIEW_CLIENT_COMPANY),
				new TViewItem(TDynaGroupView.class, ClientCompanyTypeMV.class, PersonKeys.VIEW_CLIENT_COMPANY_TYPE),
				new TViewItem(TDynaGroupView.class, ClientCompanyStatusMV.class, PersonKeys.VIEW_CLIENT_COMPANY_STATUS)
				));
	}
}

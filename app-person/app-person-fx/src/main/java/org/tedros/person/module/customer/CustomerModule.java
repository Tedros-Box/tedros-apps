/**
 * 
 */
package org.tedros.person.module.customer;

import org.tedros.core.TModule;
import org.tedros.core.annotation.TItem;
import org.tedros.core.annotation.TView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.person.PersonKeys;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.model.ClientCompany;
import org.tedros.person.model.ClientCompanyStatus;
import org.tedros.person.model.ClientCompanyType;
import org.tedros.person.model.Customer;
import org.tedros.person.model.CustomerStatus;
import org.tedros.person.model.CustomerType;
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
@TView(title=PersonKeys.MODULE_CUSTOMER,
items = {
	@TItem(title=PersonKeys.VIEW_CUSTOMERS, description=PersonKeys.VIEW_CUSTOMERS_DESC,
	model = Customer.class, modelView=CustomerMV.class),
	@TItem(title=PersonKeys.VIEW_CUSTOMER_TYPE, description=PersonKeys.VIEW_CUSTOMER_TYPE_DESC,
	model = CustomerType.class, modelView=CustomerTypeMV.class),
	@TItem(title=PersonKeys.VIEW_CUSTOMER_STATUS, description=PersonKeys.VIEW_CUSTOMER_STATUS_DESC,
	model = CustomerStatus.class, modelView=CustomerStatusMV.class),
	@TItem(title=PersonKeys.VIEW_CLIENT_COMPANY, description=PersonKeys.VIEW_CLIENT_COMPANY_DESC,
	model = ClientCompany.class, modelView=ClientCompanyMV.class),
	@TItem(title=PersonKeys.VIEW_CLIENT_COMPANY_TYPE, description=PersonKeys.VIEW_CLIENT_COMPANY_TYPE_DESC,
	model = ClientCompanyType.class, modelView=ClientCompanyTypeMV.class),
	@TItem(title=PersonKeys.VIEW_CLIENT_COMPANY_STATUS, description=PersonKeys.VIEW_CLIENT_COMPANY_STATUS_DESC,
	model = ClientCompanyStatus.class, modelView=ClientCompanyStatusMV.class)
})
@TSecurity(id=DomainApp.CUSTOMER_MODULE_ID, 
appName = PersonKeys.APP_PERSON, 
moduleName = PersonKeys.MODULE_CUSTOMER, 
allowedAccesses=TAuthorizationType.MODULE_ACCESS)
public class CustomerModule extends TModule {

}

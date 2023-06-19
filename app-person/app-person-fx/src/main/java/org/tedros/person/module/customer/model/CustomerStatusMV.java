/**
 * 
 */
package org.tedros.person.module.customer.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.page.TPage;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.query.TCondition;
import org.tedros.fx.annotation.query.TOrder;
import org.tedros.fx.annotation.query.TQuery;
import org.tedros.person.PersonKeys;
import org.tedros.person.domain.DomainApp;
import org.tedros.person.ejb.controller.IPersonStatusController;
import org.tedros.person.model.CustomerStatus;
import org.tedros.person.model.NaturalStatusMV;
import org.tedros.server.query.TCompareOp;

/**
 * @author Davis Gordon
 *
 */

@TForm(header = "", showBreadcrumBar=false, scroll=false)
@TEjbService(serviceName = IPersonStatusController.JNDI_NAME, model=CustomerStatus.class)
@TListViewPresenter(
		page=@TPage(serviceName = IPersonStatusController.JNDI_NAME,
		query = @TQuery(entity=CustomerStatus.class, condition= {
				@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME)},
			orderBy= {@TOrder(label = TUsualKey.NAME, field = "name")}
				),showSearch=true, showOrderBy=true),
		presenter=@TPresenter(decorator = @TDecorator(viewTitle=PersonKeys.VIEW_CUSTOMER_STATUS,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=true)))
@TSecurity(id=DomainApp.CUSTOMER_STATUS_FORM_ID, appName = PersonKeys.APP_PERSON,
	moduleName = PersonKeys.MODULE_CUSTOMER, viewName = PersonKeys.VIEW_CUSTOMER_STATUS,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT,
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class CustomerStatusMV extends NaturalStatusMV<CustomerStatus> {

	public CustomerStatusMV(CustomerStatus entity) {
		super(entity);
	}

}

/**
 * 
 */
package org.tedros.person.module.company.model;

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
import org.tedros.person.ejb.controller.IPersonTypeController;
import org.tedros.person.model.NaturalTypeMV;
import org.tedros.person.model.StaffType;
import org.tedros.server.query.TCompareOp;

/**
 * @author Davis Gordon
 *
 */

@TForm(name = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = IPersonTypeController.JNDI_NAME, model=StaffType.class)
@TListViewPresenter(
		page=@TPage(serviceName = IPersonTypeController.JNDI_NAME,
		query = @TQuery(entity=StaffType.class, condition= {
				@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME)},
			orderBy= {@TOrder(label = TUsualKey.NAME, field = "name")}
				),showSearch=true, showOrderBy=true),
		presenter=@TPresenter(decorator = @TDecorator(viewTitle=PersonKeys.VIEW_STAFF_TYPE,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.STAFF_TYPE_FORM_ID, appName = PersonKeys.APP_PERSON,
	moduleName = PersonKeys.MODULE_LEGAL_PERSON, viewName = PersonKeys.VIEW_STAFF_TYPE,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class StaffTypeMV extends NaturalTypeMV<StaffType> {
	
	public StaffTypeMV(StaffType entity) {
		super(entity);
	}
}

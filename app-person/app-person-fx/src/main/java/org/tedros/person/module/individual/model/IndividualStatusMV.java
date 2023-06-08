/**
 * 
 */
package org.tedros.person.module.individual.model;

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
import org.tedros.person.model.NaturalStatus;
import org.tedros.person.model.NaturalStatusMV;
import org.tedros.server.query.TCompareOp;

/**
 * @author Davis Gordon
 *
 */

@TForm(name = "", showBreadcrumBar=false, scroll=false)
@TEjbService(serviceName = IPersonStatusController.JNDI_NAME, model=NaturalStatus.class)
@TListViewPresenter(
		page=@TPage(serviceName = IPersonStatusController.JNDI_NAME,
		query = @TQuery(entity=NaturalStatus.class, condition= {
				@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME)},
			orderBy= {@TOrder(label = TUsualKey.NAME, field = "name")}
				),showSearch=true, showOrderBy=true),
		presenter=@TPresenter(decorator = @TDecorator(viewTitle=PersonKeys.VIEW_NATURAL_PERSON_STATUS,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=true)))
@TSecurity(id=DomainApp.NATURAL_PERSON_STATUS_FORM_ID, appName = PersonKeys.APP_PERSON,
	moduleName = PersonKeys.MODULE_NATURAL_PERSON, viewName = PersonKeys.VIEW_NATURAL_PERSON_STATUS,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT,
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class IndividualStatusMV extends NaturalStatusMV<NaturalStatus> {

	public IndividualStatusMV(NaturalStatus entity) {
		super(entity);
	}

}

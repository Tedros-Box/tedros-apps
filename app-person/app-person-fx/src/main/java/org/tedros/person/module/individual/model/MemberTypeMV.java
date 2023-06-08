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
import org.tedros.person.ejb.controller.IPersonTypeController;
import org.tedros.person.model.MemberType;
import org.tedros.person.model.NaturalTypeMV;
import org.tedros.server.query.TCompareOp;

/**
 * @author Davis Gordon
 *
 */

@TForm(name = "", showBreadcrumBar=false, scroll=false)
@TEjbService(serviceName = IPersonTypeController.JNDI_NAME, model=MemberType.class)
@TListViewPresenter(
		page=@TPage(serviceName = IPersonTypeController.JNDI_NAME,
		query = @TQuery(entity=MemberType.class, condition= {
				@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME)},
			orderBy= {@TOrder(label = TUsualKey.NAME, field = "name")}
				),showSearch=true, showOrderBy=true),
		presenter=@TPresenter(decorator = @TDecorator(viewTitle=PersonKeys.VIEW_MEMBER_TYPE,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=true)))
@TSecurity(id=DomainApp.MEMBER_TYPE_FORM_ID, appName = PersonKeys.APP_PERSON,
	moduleName = PersonKeys.MODULE_NATURAL_PERSON, viewName = PersonKeys.VIEW_MEMBER_TYPE,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT,
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class MemberTypeMV extends NaturalTypeMV<MemberType> {

	public MemberTypeMV(MemberType entity) {
		super(entity);
	}

}

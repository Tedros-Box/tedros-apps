/**
 * 
 */
package org.tedros.samples.module.sale.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.assistant.TAiAssistant;
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
import org.tedros.sample.domain.DomainApp;
import org.tedros.sample.ejb.controller.IGenericDomainController;
import org.tedros.sample.entity.SaleStatus;
import org.tedros.samples.SmplsKey;
import org.tedros.samples.model.GenericDomainMV;
import org.tedros.samples.module.sale.assistant.SaleStatusJson;
import org.tedros.server.query.TCompareOp;

/**
 * @author Davis
 *
 */
@TForm(name = SmplsKey.FORM_SALES_STATUS, showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = IGenericDomainController.JNDI_NAME, model=SaleStatus.class)
@TListViewPresenter(
	aiAssistant=@TAiAssistant(jsonModel = SaleStatusJson.class, modelViewClass = SaleStatusMV.class, show=true),
	page=@TPage(serviceName = IGenericDomainController.JNDI_NAME,
		query = @TQuery(entity=SaleStatus.class, condition= {
				@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME)},
			orderBy= {@TOrder(label = TUsualKey.NAME, field = "name")}
				),showSearch=true, showOrderBy=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=SmplsKey.VIEW_SALES_STATUS,
		buildModesRadioButton=false),
		behavior=@TBehavior(runNewActionAfterSave=false, saveOnlyChangedModels=false, saveAllModels=true)))
@TSecurity(id=DomainApp.SALE_STATUS_FORM_ID, appName = SmplsKey.APP_SAMPLES,
	moduleName = SmplsKey.MODULE_SALES, viewName = SmplsKey.VIEW_SALES_STATUS,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class SaleStatusMV extends GenericDomainMV<SaleStatus> {

	public SaleStatusMV(SaleStatus entity) {
		super(entity);
	}

}

/**
 * 
 */
package org.tedros.samples.module.order.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.view.TAiAssistant;
import org.tedros.fx.annotation.view.TOption;
import org.tedros.fx.annotation.view.TPaginator;
import org.tedros.sample.domain.DomainApp;
import org.tedros.sample.ejb.controller.IGenericDomainController;
import org.tedros.sample.entity.OrderStatus;
import org.tedros.samples.SmplsKey;
import org.tedros.samples.model.GenericDomainMV;
import org.tedros.samples.module.order.assistant.OrderStatusJson;

/**
 * @author Davis
 *
 */
@TForm(name = SmplsKey.FORM_ORDER_STATUS, showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = IGenericDomainController.JNDI_NAME, model=OrderStatus.class)
@TListViewPresenter(
	aiAssistant=@TAiAssistant(jsonModel = OrderStatusJson.class, modelViewClass = OrderStatusMV.class, show=true),
	paginator=@TPaginator(entityClass = OrderStatus.class, serviceName = IGenericDomainController.JNDI_NAME,
		show=true, showSearch=true, searchField="name", 
		orderBy = {	@TOption(text = TUsualKey.NAME , field = "name")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=SmplsKey.VIEW_ORDER_STATUS,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false, saveOnlyChangedModels=false, saveAllModels=true)))
@TSecurity(id=DomainApp.ORDER_STATUS_FORM_ID, appName = SmplsKey.APP_SAMPLES,
	moduleName = SmplsKey.MODULE_ORDERS, viewName = SmplsKey.VIEW_ORDER_STATUS,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class OrderStatusMV extends GenericDomainMV<OrderStatus> {

	public OrderStatusMV(OrderStatus entity) {
		super(entity);
	}

}

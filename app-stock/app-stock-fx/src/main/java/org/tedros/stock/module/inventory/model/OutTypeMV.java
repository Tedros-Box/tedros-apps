/**
 * 
 */
package org.tedros.stock.module.inventory.model;

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
import org.tedros.stock.STCKKey;
import org.tedros.stock.domain.DomainApp;
import org.tedros.stock.ejb.controller.IEventTypeController;
import org.tedros.stock.entity.OutType;
import org.tedros.stock.module.inventory.assistant.OutTypeJson;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "", showBreadcrumBar=false, scroll=false)
@TEjbService(serviceName = IEventTypeController.JNDI_NAME, model=OutType.class)
@TListViewPresenter(
	aiAssistant=@TAiAssistant(jsonModel = OutTypeJson.class, modelViewClass = OutTypeMV.class, show=true),
	paginator=@TPaginator(entityClass = OutType.class, serviceName = IEventTypeController.JNDI_NAME,
		show=true, showSearchField=true, searchFieldName="name", 
		orderBy = {	@TOption(text = TUsualKey.NAME , value = "name")}),
	presenter=@TPresenter(
		decorator = @TDecorator(viewTitle=STCKKey.VIEW_OUT_TYPE, buildModesRadioButton=false),
		behavior=@TBehavior(runNewActionAfterSave=false, saveOnlyChangedModels=false)))
@TSecurity(id=DomainApp.OUT_TYPE_FORM_ID, appName = STCKKey.APP_STOCK,
	moduleName = STCKKey.MODULE_INVENTORY, viewName = STCKKey.VIEW_OUT_TYPE,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class OutTypeMV extends EventTypeMV<OutType> {

	public OutTypeMV(OutType entity) {
		super(entity);
	}

}

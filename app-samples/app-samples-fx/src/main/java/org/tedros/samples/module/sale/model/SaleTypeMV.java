/**
 * 
 */
package org.tedros.samples.module.sale.model;

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
import org.tedros.sample.entity.SaleType;
import org.tedros.samples.SmplsKey;
import org.tedros.samples.model.GenericDomainMV;
import org.tedros.samples.module.sale.assistant.SaleTypeJson;

/**
 * @author Davis
 *
 */
@TForm(name = SmplsKey.FORM_SALES_TYPE, showBreadcrumBar=true, scroll=false)
@TEjbService(serviceName = IGenericDomainController.JNDI_NAME, model=SaleType.class)
@TListViewPresenter(
	aiAssistant=@TAiAssistant(jsonModel = SaleTypeJson.class, modelViewClass = SaleTypeMV.class, show=true),
	paginator=@TPaginator(entityClass = SaleType.class, serviceName = IGenericDomainController.JNDI_NAME,
		show=true, showSearchField=true, searchFieldName="name", 
		orderBy = {	@TOption(text = TUsualKey.NAME , value = "name")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=SmplsKey.VIEW_SALES_TYPE,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false, saveOnlyChangedModels=false, saveAllModels=true)))
@TSecurity(id=DomainApp.SALE_TYPE_FORM_ID, appName = SmplsKey.APP_SAMPLES,
	moduleName = SmplsKey.MODULE_SALES, viewName = SmplsKey.VIEW_SALES_TYPE,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class SaleTypeMV extends GenericDomainMV<SaleType> {

	public SaleTypeMV(SaleType entity) {
		super(entity);
	}

}

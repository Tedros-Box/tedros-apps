/**
 * 
 */
package org.tedros.ifood.module.order.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
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
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.model.TEntityModelView;
import org.tedros.ifood.IFOODKey;
import org.tedros.ifood.domain.DomainApp;
import org.tedros.ifood.ejb.controller.IIFoodConfigController;
import org.tedros.ifood.entity.IFoodConfig;
import org.tedros.server.query.TCompareOp;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Dun
 *
 */
@TForm(header = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = IIFoodConfigController.JNDI_NAME, model=IFoodConfig.class)
@TListViewPresenter(
	page=@TPage(serviceName = IIFoodConfigController.JNDI_NAME,
		query = @TQuery(entity=IFoodConfig.class, condition= {
				@TCondition(field = "name", operator=TCompareOp.LIKE, label=TUsualKey.NAME)},
			orderBy= {@TOrder(label = TUsualKey.NAME, field = "name")}
		),showSearch=true, showOrderBy=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=IFOODKey.MY_APP_MY_VIEW,
		buildModesRadioButton=false),
		behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.IFOOD_CONFIG__FORM_ID, appName = IFOODKey.APP_MY_APP,
	moduleName = IFOODKey.MODULE_MY_APP, viewName = IFOODKey.MY_APP_MY_VIEW,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class IFoodConfigMV extends TEntityModelView<IFoodConfig> {

	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=120, required = true, 
	node=@TNode(requestFocus=true, parse = true) )
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(maxLength=1024, wrapText=true, prefRowCount=4)
	private SimpleStringProperty description;
	
	public IFoodConfigMV(IFoodConfig entity) {
		super(entity);
	}

	@Override
	public SimpleStringProperty toStringProperty() {
		return name;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getDescription() {
		return description;
	}

	public void setDescription(SimpleStringProperty description) {
		this.description = description;
	}
}

/**
 * 
 */
package org.tedros.services.module.service.model;

import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.annotation.security.TSecurity;
import org.tedros.fx.TUsualKey;
import org.tedros.fx.annotation.control.TLabel;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.control.TTextField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.scene.TNode;
import org.tedros.fx.annotation.view.TOption;
import org.tedros.fx.annotation.view.TPaginator;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.services.ServKey;
import org.tedros.services.domain.DomainApp;
import org.tedros.services.ejb.controller.IServiceTypeController;
import org.tedros.services.model.ServiceType;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "", showBreadcrumBar=false, scroll=true)
@TEjbService(serviceName = IServiceTypeController.JNDI_NAME, model=ServiceType.class)
@TListViewPresenter(
	paginator=@TPaginator(entityClass = ServiceType.class, serviceName = IServiceTypeController.JNDI_NAME,
		show=true, showSearchField=true, searchFieldName="name", 
		orderBy = {	@TOption(text = TUsualKey.NAME , value = "name")}),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=ServKey.VIEW_SERVICE_TYPE,
		buildModesRadioButton=false),
	behavior=@TBehavior(runNewActionAfterSave=false)))
@TSecurity(id=DomainApp.SERVICE_TYPE_FORM_ID, appName = ServKey.APP_SERVICE,
	moduleName = ServKey.MODULE_SERVICES, viewName = ServKey.VIEW_SERVICE_TYPE,
	allowedAccesses={TAuthorizationType.VIEW_ACCESS, TAuthorizationType.EDIT, TAuthorizationType.READ, 
					TAuthorizationType.SAVE, TAuthorizationType.DELETE, TAuthorizationType.NEW})
public class ServiceTypeMV extends TEntityModelView<ServiceType> {

	@TLabel(text=TUsualKey.NAME)
	@TTextField(maxLength=120, required = true, 
	node=@TNode(requestFocus=true, parse = true) )
	private SimpleStringProperty name;
	
	@TLabel(text=TUsualKey.DESCRIPTION)
	@TTextAreaField(maxLength=1024, wrapText=true, prefRowCount=4)
	private SimpleStringProperty description;
	
	public ServiceTypeMV(ServiceType entity) {
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